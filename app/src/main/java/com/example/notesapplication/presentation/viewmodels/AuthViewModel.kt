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
import com.example.notesapplication.domain.usecase.SignupUseCase
import com.example.notesapplication.domain.validation.ValidateEmail
import com.example.notesapplication.domain.validation.ValidatePassword
import com.example.notesapplication.domain.validation.ValidateUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val signupUseCase: SignupUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val validateUsername: ValidateUsername,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
) : ViewModel() {

    private val TAG = AuthViewModel::class.simpleName
    private val _authUIstate = MutableStateFlow(AuthUIstate())
    val authUIstate: StateFlow<AuthUIstate> = _authUIstate

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _signupSuccess = MutableStateFlow(false)
    val signupSuccess : StateFlow<Boolean> = _signupSuccess


    fun onEvent(event: UIEvent) {
        when (event) {

            is UIEvent.EmailChanged -> {
                _authUIstate.update { it.copy(email = event.email, emailError = null) }
            }

            is UIEvent.PasswordChanged -> {
                _authUIstate.update {
                    it.copy(
                        password = event.password,
                        passwordError = null
                    )
                }
            }

            is UIEvent.UserNameChanged -> {
                _authUIstate.update {
                    it.copy(
                        userName = event.userName,
                        userNameError = null
                    )
                }
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
        val state = _authUIstate.value

        val usernameResult = validateUsername(state.userName)
        val usernameError = if (!usernameResult.successful) usernameResult.erroMessage else null

        val passwordResult = validatePassword(state.password)
        val passwordError = if (!passwordResult.successful) "Password invalid" else null


        // If there are validation errors, update state so UI can show them
        if (usernameError != null || passwordError != null) {
            _authUIstate.update {
                it.copy(
                    userNameError = usernameError,
                    passwordError = passwordError
                )
            }
            return
        }

        viewModelScope.launch {
            try {
                _authUIstate.update {
                    it.copy(
                        isLoading = true
                    )
                }
                val response = loginUsecase(authUIstate.value.userName, authUIstate.value.password)
                if (response.isSuccessful) {
                    _loginSuccess.value = true
                    saveTokenUseCase(response.body().toString())
                }

                _authUIstate.update {
                    it.copy(
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Login failed", e)
                _authUIstate.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun callSignUpAPI() {
        Log.d(TAG, "CallSignUpAPI")
        val state = _authUIstate.value

        val usernameResult = validateUsername(state.userName)
        val usernameError = if (!usernameResult.successful) usernameResult.erroMessage else null

        val emailResult = validateEmail(state.email)
        val emailError = if(!emailResult.successful) emailResult.erroMessage else null

        val passwordResult = validatePassword(state.password)
        val passwordError = if (!passwordResult.successful) passwordResult.erroMessage else null


        // If there are validation errors, update state so UI can show them
        if (usernameError != null || passwordError != null) {
            _authUIstate.update {
                it.copy(
                    userNameError = usernameError,
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }

        viewModelScope.launch {
            try {
                _authUIstate.update {
                    it.copy(
                        isLoading = true
                    )
                }
                val response = signupUseCase(
                    authUIstate.value.userName,
                    authUIstate.value.email,
                    authUIstate.value.password
                )
                if (response.isSuccessful) {
                    _signupSuccess.value = true
                }

                _authUIstate.update {
                    it.copy(
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Login failed", e)
                _authUIstate.update { it.copy(isLoading = false) }
            }
        }
    }


}