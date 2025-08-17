package com.example.notesapplication.domain.usecase

import com.example.notesapplication.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repository: NotesRepository) {
    suspend operator fun invoke(id: String) = repository.deleteNote(id)
}