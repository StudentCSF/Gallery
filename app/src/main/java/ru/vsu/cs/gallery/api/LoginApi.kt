package ru.vsu.cs.gallery.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.vsu.cs.gallery.model.dto.request.LoginRequest
import ru.vsu.cs.gallery.model.dto.response.AuthInfo

interface LoginApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthInfo
}