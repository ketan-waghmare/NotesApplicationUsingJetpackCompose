package com.example.notesapplication.domain.usecase

import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.repository.NotesRepository

class GetNoteByIdUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(id: String) : Note {
        return repository.getNoteById(id)
    }
}