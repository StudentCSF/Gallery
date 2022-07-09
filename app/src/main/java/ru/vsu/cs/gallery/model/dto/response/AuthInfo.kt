package ru.vsu.cs.gallery.model.dto.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthInfo(
    @SerializedName("token") val token: String,
    @SerializedName("user_info") val userInfo: User
) : Serializable