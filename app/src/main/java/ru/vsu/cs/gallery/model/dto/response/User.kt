package ru.vsu.cs.gallery.model.dto.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id") val id: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("lastName") val lastName: String = "",
    @SerializedName("avatar") val avatar: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("about") val about: String = ""
) : Serializable