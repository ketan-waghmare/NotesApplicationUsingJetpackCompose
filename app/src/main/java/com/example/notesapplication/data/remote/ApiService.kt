package com.example.notesapplication.data.remote

import com.example.notesapplication.domain.model.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("public/login")
    suspend fun login(@Body request: LoginRequest): Response<String>

    @GET("/api/notes")
    suspend fun getNotes(@Header("Authorization") token: String): List<Note>

    @POST("/api/notes/create-note")
    suspend fun createNote(
        @Header("Authorization") token: String,
        @Body note: NoteRequest
    ): Note
}