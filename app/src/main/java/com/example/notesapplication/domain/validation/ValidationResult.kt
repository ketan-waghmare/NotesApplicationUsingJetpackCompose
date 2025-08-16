package com.example.notesapplication.domain.validation

import android.os.Message

data class ValidationResult(
    val successful: Boolean,
    val erroMessage: String? = ""
)
