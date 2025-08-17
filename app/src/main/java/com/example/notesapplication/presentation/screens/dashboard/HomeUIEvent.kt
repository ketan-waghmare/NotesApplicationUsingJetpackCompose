package com.example.notesapplication.presentation.screens.dashboard

import com.example.notesapplication.domain.model.Note

sealed class HomeUIEvent {
    data class ShowMessage(val text: String) : HomeUIEvent()
    data class DeletedWithUndo(val note: Note, val index: Int) : HomeUIEvent()
}