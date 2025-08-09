package com.example.notesapplication.data.repository

import com.example.notesapplication.data.remote.ApiService
import com.example.notesapplication.data.remote.LoginRequest
import com.example.notesapplication.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(
        userName: String,
        password: String
    ): Response<String> {
        return apiService.login(LoginRequest(userName,password))
    }
}