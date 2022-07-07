package ru.vsu.cs.gallery.model.dto.response

import java.io.Serializable

data class Picture(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Int
) : Serializable