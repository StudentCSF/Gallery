package ru.vsu.cs.gallery.model.dto.response

import java.io.Serializable

data class Error(
    val code: Int,
    val reason: String
) : Serializable