package ru.vsu.cs.gallery.model.dto.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Picture(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("photoUrl") val photoUrl: String = "",
    @SerializedName("publicationDate") val publicationDate: Int = -1
) : Serializable