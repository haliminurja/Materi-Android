package id.ac.unuja.sampel.server.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("note")
    suspend fun getNotes(): Response<NoteResponseList>

    @GET("note/{id}")
    suspend fun getNoteDetail(
        @Path("id") id: Int,
    ): Response<NoteResponse>

    @POST("note")
    suspend fun createNote(@Body note: NoteRequest): Response<NoteResponse>

    @PUT("note/{id}")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Body note: NoteRequest
    ): Response<NoteResponse>

    @DELETE("note/{id}")
    suspend fun deleteNote(@Path("id") id: Int): Response<NoteResponse>
}