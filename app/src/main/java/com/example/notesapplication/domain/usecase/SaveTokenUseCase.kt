package com.example.notesapplication.domain.usecase

import com.example.notesapplication.data.local.TokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val repo: TokenRepository) {
    suspend operator fun invoke(token: String) = repo.saveToken(token)
}