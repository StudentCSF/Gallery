package ru.vsu.cs.gallery.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.vsu.cs.gallery.model.dto.request.LoginRequest
import ru.vsu.cs.gallery.model.dto.response.AuthInfo

interface LoginService {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<AuthInfo>
}