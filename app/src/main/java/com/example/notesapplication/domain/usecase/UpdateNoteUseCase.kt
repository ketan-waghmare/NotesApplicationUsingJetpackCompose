package com.example.notesapplication.domain.usecase

import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.repository.NotesRepository

class UpdateNoteUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: String, title: String,description: String) : Note {
        return repository.updateNote(id,title,description)
    }
}