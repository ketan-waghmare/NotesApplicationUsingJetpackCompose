package com.example.notesapplication.domain.usecase

import com.example.notesapplication.domain.repository.AuthRepository
import retrofit2.Response

class LoginUsecase(private val repository: AuthRepository) {
    suspend operator fun invoke(userName: String,password: String) : Response<String> {
        return repository.login(userName,password)
    }
}