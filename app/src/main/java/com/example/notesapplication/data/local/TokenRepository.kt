package com.example.notesapplication.data.local

interface TokenRepository {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
}