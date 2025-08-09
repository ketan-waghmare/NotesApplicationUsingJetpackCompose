package com.example.notesapplication.di

import com.example.notesapplication.data.local.TokenRepository
import com.example.notesapplication.data.local.TokenRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun bindTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository = impl

}