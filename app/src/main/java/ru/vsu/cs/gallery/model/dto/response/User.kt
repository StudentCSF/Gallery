package ru.vsu.cs.gallery.model.dto.response

data class User(
    val id: String,
    val phone: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val city: String,
    val about: String
) {
}