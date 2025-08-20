package com.example.notesapplication.data.remote

import com.example.notesapplication.domain.validation.ValidatePassword

data class SignupRequest(
    val userName: String,
    val email: String,
    val password: String
)
