package com.example.notesapplication.presentation.screens.addnotes

data class CreateNoteUIState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)