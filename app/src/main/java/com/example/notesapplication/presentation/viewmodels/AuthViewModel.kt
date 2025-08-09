package com.example.notesapplication.presentation.viewmodels

import android.media.metrics.Event
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.domain.usecase.GetTokenUseCase
import com.example.notesapplication.domain.usecase.LoginUsecase
import com.example.notesapplication.domain.usecase.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val TAG = AuthViewModel::class.simpleName

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    var authUIstate = mutableStateOf(AuthUIstate())

    fun onEvent(event: UIEvent) {
        when(event) {

            is UIEvent.EmailChanged -> {
                authUIstate.value = authUIstate.value.copy(
                    email = event.email
                )
            }

            is UIEvent.PasswordChanged -> {
                authUIstate.value = authUIstate.value.copy(
                    password = event.password
                )

                print("UIState_Password = "+event.password)
            }

            is UIEvent.UserNameChanged -> {
                authUIstate.value = authUIstate.value.copy(
                    userName = event.userName
                )

                print("UIState_UserName =" +event.userName)
            }

            is UIEvent.LoginButtonClicked -> {
                callLoginAPI()
            }

            is UIEvent.RegisterButtonClicked -> {
                callSignUpAPI()
            }
        }
    }

    private fun callLoginAPI() {
        val state = authUIstate.value
        viewModelScope.launch {
            try {
                authUIstate.value = state.copy(isLoading = true)

                Log.d(TAG,"requestData = ${authUIstate.value.userName + authUIstate.value.password}")

                val response = loginUsecase(authUIstate.value.userName, authUIstate.value.password)

                if(response.isSuccessful) {
                    _loginSuccess.value = true
                    saveTokenUseCase(response.body().toString())
                }

                authUIstate.value = state.copy(
                    isLoading = false,
                )

            } catch (e: Exception) {
                Log.e(TAG, "Login failed", e)
                authUIstate.value = state.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Unknown error"
                )
            }
        }
    }

    private fun callSignUpAPI() {
        Log.d(TAG,"CallSignUpAPI")
    }


}