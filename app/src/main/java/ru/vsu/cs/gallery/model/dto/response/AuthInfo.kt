package ru.vsu.cs.gallery.model.dto.response

import java.io.Serializable

data class AuthInfo(
    val token: String,
    val user_info: User
) : Serializable