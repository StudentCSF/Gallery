package ru.vsu.cs.gallery.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppConfig {

    companion object {
        private const val BASE_URL: String = "https://pictures.chronicker.fun/api/"

        val RETROFIT: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().build())
            .build()
    }
}