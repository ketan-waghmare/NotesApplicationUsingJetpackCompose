package com.example.notesapplication.presentation.screens.notedetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.repository.NotesRepository
import com.example.notesapplication.domain.usecase.GetNoteByIdUseCase
import com.example.notesapplication.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    private val _noteDetailSuccess = mutableStateOf(false)
    val noteDetailSuccess: State<Boolean> = _noteDetailSuccess
    var noteDetailState = mutableStateOf(NoteDetailUIState())

    var loading = mutableStateOf(false)

    fun onEvent(event: NoteDetailUIEvent) {
        when(event) {
            is NoteDetailUIEvent.DescriptionChanged -> {
                noteDetailState.value = noteDetailState.value.copy(
                    description = event.description
                )
            }

            is NoteDetailUIEvent.TitleChanged -> {
                noteDetailState.value = noteDetailState.value.copy(
                    title = event.title
                )
            }

            NoteDetailUIEvent.updateNoteClicked -> {
                updateNote(
                    noteDetailState.value.noteId,
                    noteDetailState.value.title,
                     noteDetailState.value.description)
            }
        }
    }

    fun getNotesDetails(noteId: String) {
        viewModelScope.launch {
            try {

                loading.value = true
                val response = getNoteByIdUseCase(noteId)
                noteDetailState.value = noteDetailState.value.copy(
                    noteId = response.id,
                    title = response.title,
                    description = response.description
                )
                loading.value = false
            } catch (e: Exception) {
                loading.value = false
                Log.d("NoteDetailViewModel", " Exception = "+e.message)
            }
        }
    }

    fun updateNote(noteId: String, title: String, description: String){
        viewModelScope.launch {
            try {
                loading.value = true
                val response = updateNoteUseCase(noteId,title,description)
                if(response.id.isNotBlank()) {
                    _noteDetailSuccess.value = true
                    loading.value = false
                    Log.d("NoteDetailsViewModel","Notes Updated Successfully")
                }
            } catch (e: Exception) {
                loading.value = false
                Log.d("NoteDetailsViewModel","Failed to update")
            }
        }
    }
}