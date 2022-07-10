package ru.vsu.cs.gallery.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.vsu.cs.gallery.model.dto.request.LoginRequest
import ru.vsu.cs.gallery.model.dto.response.AuthInfo

interface UserApi {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<AuthInfo>

    @POST("auth/logout")
    fun logout(@Header("Authorization") token: String): Call<Void>
}