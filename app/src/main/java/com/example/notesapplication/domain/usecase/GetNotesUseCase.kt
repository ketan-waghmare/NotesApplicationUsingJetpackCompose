package com.example.notesapplication.domain.usecase

import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.repository.NotesRepository

class GetNotesUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(): List<Note> {
        return repository.getNotes()
    }
}