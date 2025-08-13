package com.example.notesapplication.presentation.screens.notedetails

data class NoteDetailUIState (
    val noteId: String = "",
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

