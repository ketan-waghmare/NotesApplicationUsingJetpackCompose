package com.example.notesapplication.data.remote

import com.example.notesapplication.domain.model.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("public/login")
    suspend fun login(@Body request: LoginRequest): Response<String>

    @POST("public/create-user")
    suspend fun signup(@Body request: SignupRequest) : Response<Void>

    @GET("/api/notes")
    suspend fun getNotes(@Header("Authorization") token: String): List<Note>

    @POST("/api/notes/create-note")
    suspend fun createNote(
        @Header("Authorization") token: String,
        @Body note: NoteRequest
    ): Note

    @GET("/api/notes/id/{id}")
    suspend fun getNoteById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ) : Note

    @PUT("/api/notes/id/{id}")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: NoteRequest
    ) : Note


    @DELETE("/api/notes/id/{id}")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<Void>

}