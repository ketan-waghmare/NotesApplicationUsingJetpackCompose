package com.example.notesapplication.domain.usecase

import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.repository.NotesRepository
import okhttp3.Response

class AddNoteUseCase(private val repository: NotesRepository) {
    suspend operator fun invoke(title: String, description: String) : Note {
        return repository.createNote(title,description)
    }
}