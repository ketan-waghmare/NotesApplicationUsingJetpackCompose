package com.example.notesapplication.domain.validation

import javax.inject.Inject

class ValidateUsername @Inject constructor(){
    operator fun invoke(username: String): ValidationResult {
        if (username.isBlank()) return ValidationResult(false, "Username can't be empty")
        if (username.length < 3) return ValidationResult(false, "Username must be ≥ 3 chars")
        return ValidationResult(true)
    }
}