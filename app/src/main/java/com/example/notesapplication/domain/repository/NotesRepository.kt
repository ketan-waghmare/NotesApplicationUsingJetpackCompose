package com.example.notesapplication.domain.repository

import com.example.notesapplication.domain.model.Note

interface NotesRepository {
    suspend fun getNotes(): List<Note>
}