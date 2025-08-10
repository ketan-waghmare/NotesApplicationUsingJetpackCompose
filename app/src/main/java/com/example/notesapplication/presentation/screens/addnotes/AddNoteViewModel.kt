package com.example.notesapplication.presentation.screens.addnotes

import android.media.metrics.Event
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.data.remote.NoteRequest
import com.example.notesapplication.domain.repository.NotesRepository
import com.example.notesapplication.domain.usecase.AddNoteUseCase
import com.example.notesapplication.presentation.viewmodels.AuthUIstate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    var title = mutableStateOf("")
    var description = mutableStateOf("")

    private val _addNoteSuccess = mutableStateOf(false)
    val addNoteSuccess: State<Boolean> = _addNoteSuccess

    var addNoteUIstate = mutableStateOf(CreateNoteUIState())

    fun onEvent(event: CreateNoteUIEvent) {
        when(event) {
            is CreateNoteUIEvent.DescriptionChanged -> {
                addNoteUIstate.value = addNoteUIstate.value.copy(
                    description = event.description
                )
            }

            is CreateNoteUIEvent.TitleChanged -> {
                addNoteUIstate.value = addNoteUIstate.value.copy(
                    title = event.title
                )
            }

            CreateNoteUIEvent.SaveNoteClicked -> {
                callAddNoteAPI()
            }
        }
    }

    private fun callAddNoteAPI() {
        val state = addNoteUIstate.value
        viewModelScope.launch {
            try {
                addNoteUIstate.value = state.copy(isLoading = true)

                Log.d("AddNoteViewModel","requestData = ${addNoteUIstate.value.title + addNoteUIstate.value.description}")

                val response = addNoteUseCase(addNoteUIstate.value.title, addNoteUIstate.value.description)

                if (response.id.isNotBlank()) {
                    _addNoteSuccess.value = true
                } else {
                   Log.d("AddNoteViewModel","Failed to create a note")
                }

                addNoteUIstate.value = state.copy(
                    isLoading = false,
                )

            } catch (e: Exception) {
                Log.e("AddNoteViewModel", "Login failed", e)
                addNoteUIstate.value = state.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Something went wrong"
                )
            }
        }
    }
}