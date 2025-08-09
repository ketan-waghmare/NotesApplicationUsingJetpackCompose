package com.example.notesapplication.domain.usecase

import com.example.notesapplication.data.local.TokenRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repo: TokenRepository) {
    suspend operator fun invoke(): String? = repo.getToken()
}