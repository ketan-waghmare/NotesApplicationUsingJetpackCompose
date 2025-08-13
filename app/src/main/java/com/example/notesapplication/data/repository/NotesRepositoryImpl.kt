package com.example.notesapplication.data.repository

import com.example.notesapplication.data.local.TokenRepository
import com.example.notesapplication.data.remote.ApiService
import com.example.notesapplication.data.remote.NoteRequest
import com.example.notesapplication.domain.model.Note
import com.example.notesapplication.domain.repository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenRepository: TokenRepository
) : NotesRepository {
    override suspend fun getNotes(): List<Note> {
        val token = tokenRepository.getToken()
        return apiService.getNotes("Bearer $token")
    }

    override suspend fun createNote(title: String, description: String): Note {
        val token = tokenRepository.getToken()
        return apiService.createNote("Bearer $token", NoteRequest(title,description))
    }

    override suspend fun getNoteById(id: String): Note {
        val token = tokenRepository.getToken()
        return apiService.getNoteById("Bearer $token",id)
    }

    override suspend fun updateNote(
        id: String,
        title: String,
        description: String
    ): Note {
        val token = tokenRepository.getToken()
        return apiService.updateNote("Bearer $token",id, NoteRequest(title,description))
    }
}