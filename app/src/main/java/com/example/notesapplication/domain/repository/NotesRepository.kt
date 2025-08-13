package com.example.notesapplication.domain.repository

import com.example.notesapplication.domain.model.Note
import okhttp3.Response

interface NotesRepository {
    suspend fun getNotes(): List<Note>

    suspend fun createNote(title: String, description: String): Note

    suspend fun getNoteById(id: String) : Note

    suspend fun updateNote(id: String,title: String,description: String) : Note
}