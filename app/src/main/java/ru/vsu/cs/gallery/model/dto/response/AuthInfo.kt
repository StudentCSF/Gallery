package ru.vsu.cs.gallery.model.dto.response

data class AuthInfo(val token: String, val user_info: User) {
}