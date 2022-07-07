package ru.vsu.cs.gallery.model.dto.response

data class Picture(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Int
) {
}