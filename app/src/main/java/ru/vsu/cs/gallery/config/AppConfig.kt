package ru.vsu.cs.gallery.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.gallery.api.LoginApi

class AppConfig {

    companion object {
        private const val BASE_URL: String = "https://pictures.chronicker.fun/api/"

        private val RETROFIT: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().build())
            .build()

        val LOGIN_API: LoginApi = RETROFIT.create(LoginApi::class.java)
    }
}