package com.example.notesapplication.presentation.viewmodels

data class AuthUIstate(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val userNameError: String? = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val errorMessage: String? = null

)