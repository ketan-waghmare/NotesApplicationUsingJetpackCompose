package com.example.notesapplication.presentation.screens.dashboard

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.usecase.DeleteNoteUseCase
import com.example.notesapplication.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    var notesList = mutableStateOf<List<Note>>(emptyList())
        private set

    var isLoading = mutableStateOf(false)
        private set

    // one-off events (snackbar, undo prompt, messages)
    private val _events = MutableSharedFlow<HomeUIEvent>()
    val events: SharedFlow<HomeUIEvent> = _events

    // store last deleted to support undo (index included to restore position)
    private var lastDeleted: Pair<Note, Int>? = null

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                notesList.value = getNotesUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading.value = false
        }
    }

    /** Optimistic delete: remove locally, call API, then show Undo */
    fun onDeleteRequest(note: Note) {
        val current = notesList.value
        val index = current.indexOfFirst { it.id == note.id }
        if (index == -1) return

        // Optimistically remove from UI
        val updated = current.toMutableList().also { it.removeAt(index) }
        notesList.value = updated
        lastDeleted = note to index

        viewModelScope.launch {
            // Call API
            var isRemoved = false
            try{
                val response = deleteNoteUseCase(note.id)
                if(response.isSuccessful) {
                    isRemoved = true
                } else {
                    isRemoved = false
                }

            } catch (e : Exception) {
                Log.d("DeletAPIOkStatus"," API call failed in catch = false")
            }

            if (!isRemoved) {
                // Restore if API failed
                restoreLastDeleted()
                _events.emit(HomeUIEvent.ShowMessage("Delete failed. Please try again."))
                return@launch
            }

            // Offer undo on success
            _events.emit(HomeUIEvent.DeletedWithUndo(note, index))
        }
    }

    /** User tapped Undo in snackbar */
    fun undoDelete(note: Note, index: Int) {
        val list = notesList.value.toMutableList()
        val pos = index.coerceIn(0, list.size)
        list.add(pos, note)
        notesList.value = list
        lastDeleted = null

        // OPTIONAL: if your backend requires a “restore”, call createNote() here
        // viewModelScope.launch { createNoteUseCase(note) }
    }

    /** Internal restore helper for API failure */
    private fun restoreLastDeleted() {
        val (note, index) = lastDeleted ?: return
        val list = notesList.value.toMutableList()
        val pos = index.coerceIn(0, list.size)
        list.add(pos, note)
        notesList.value = list
        lastDeleted = null
    }
}