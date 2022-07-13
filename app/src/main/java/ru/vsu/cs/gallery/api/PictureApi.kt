package ru.vsu.cs.gallery.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import ru.vsu.cs.gallery.model.dto.response.Picture

interface PictureApi {

    @GET("picture")
    fun getPictures(
        @Header("Authorization") token: String
    ): Call<List<Picture>>
}