package com.example.notesapplication.domain.usecase

import com.example.notesapplication.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userName: String,email: String,password: String) : Response<Void> {
        return repository.signup(userName,email ,password)
    }
}