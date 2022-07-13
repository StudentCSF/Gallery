package ru.vsu.cs.gallery.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.gallery.api.PictureApi
import ru.vsu.cs.gallery.api.UserApi

class AppConfig {

    companion object {
        private const val BASE_URL: String = "https://pictures.chronicker.fun/api/"

        const val APP_PREFERENCES: String = "app"
        const val TOKEN: String = "token"
        const val USER: String = "user"

        private val RETROFIT: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().build())
            .build()

        val USER_API: UserApi = RETROFIT.create(UserApi::class.java)
        val PICTURE_API: PictureApi = RETROFIT.create(PictureApi::class.java)
    }
}