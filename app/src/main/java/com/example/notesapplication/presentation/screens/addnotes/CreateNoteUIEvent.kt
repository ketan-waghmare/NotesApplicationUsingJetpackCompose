package com.example.notesapplication.presentation.screens.addnotes

sealed class CreateNoteUIEvent {
    data class TitleChanged(val title: String) : CreateNoteUIEvent()
    data class DescriptionChanged(val description: String) : CreateNoteUIEvent()
    object SaveNoteClicked : CreateNoteUIEvent()
}