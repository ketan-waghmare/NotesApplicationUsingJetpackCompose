package com.example.notesapplication.domain.validation

import javax.inject.Inject

class ValidatePassword @Inject constructor(){
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) return ValidationResult(false, "Password can't be empty")
        if (password.length < 6) return ValidationResult(false, "Password must be ≥ 6 chars")
        return ValidationResult(true)
    }
}