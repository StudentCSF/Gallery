package ru.vsu.cs.gallery.model.dto.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String
    ) : Serializable