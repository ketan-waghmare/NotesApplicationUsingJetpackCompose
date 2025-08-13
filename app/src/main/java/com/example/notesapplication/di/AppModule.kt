package com.example.notesapplication.di


import android.content.Context
import com.example.notesapplication.data.local.TokenRepository
import com.example.notesapplication.data.local.TokenRepositoryImpl
import com.example.notesapplication.data.remote.ApiService
import com.example.notesapplication.data.repository.AuthRepositoryImpl
import com.example.notesapplication.data.repository.NotesRepositoryImpl
import com.example.notesapplication.domain.repository.AuthRepository
import com.example.notesapplication.domain.repository.NotesRepository
import com.example.notesapplication.domain.usecase.AddNoteUseCase
import com.example.notesapplication.domain.usecase.GetNoteByIdUseCase
import com.example.notesapplication.domain.usecase.GetNotesUseCase
import com.example.notesapplication.domain.usecase.LoginUsecase
import com.example.notesapplication.domain.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://notes-application-d2507a4e23e7.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    @Provides
    fun provideLoginUseCase(repository: AuthRepository): LoginUsecase {
        return LoginUsecase(repository)
    }

    @Provides
    fun provideNotesRepository(
        apiService: ApiService,
        tokenRepository: TokenRepository
    ): NotesRepository {
        return NotesRepositoryImpl(apiService, tokenRepository)
    }

    @Provides
    fun provideGetNotesUseCase(
        notesRepository: NotesRepository
    ): GetNotesUseCase {
        return GetNotesUseCase(notesRepository)
    }

    @Provides
    fun provideAddNoteUseCase(repository: NotesRepository): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }

    @Provides
    fun provideUpdateNoteUseCase(repository: NotesRepository) : UpdateNoteUseCase {
        return UpdateNoteUseCase(repository)
    }

    @Provides
    fun provideGetNoteByIdUseCase(repository: NotesRepository) : GetNoteByIdUseCase {
        return GetNoteByIdUseCase(repository)
    }
}