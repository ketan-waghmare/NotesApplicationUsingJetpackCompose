package com.example.notesapplication.domain.validation

import javax.inject.Inject

class ValidateEmail @Inject constructor(){
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) return ValidationResult(false, "Email can't be empty")
        return ValidationResult(true)
    }
}