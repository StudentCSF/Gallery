package ru.vsu.cs.gallery.model.dto.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Error(
    @SerializedName("code") val code: Int = -1,
    @SerializedName("reason") val reason: String = ""
) : Serializable