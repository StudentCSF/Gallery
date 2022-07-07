package ru.vsu.cs.gallery.model.dto.request

import java.io.Serializable

data class LoginRequest(
    val phone: String,
    val password: String
    ) : Serializable