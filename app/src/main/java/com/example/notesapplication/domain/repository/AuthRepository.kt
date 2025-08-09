package com.example.notesapplication.domain.repository

import retrofit2.Response

interface AuthRepository {
    suspend fun login(userName: String, password: String) : Response<String>
}