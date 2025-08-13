package com.example.notesapplication.presentation.screens.notedetails

import com.example.notesapplication.presentation.screens.addnotes.CreateNoteUIEvent

sealed class NoteDetailUIEvent {
    data class TitleChanged(val title: String) : NoteDetailUIEvent()
    data class DescriptionChanged(val description: String) : NoteDetailUIEvent()
    object updateNoteClicked : NoteDetailUIEvent()
}