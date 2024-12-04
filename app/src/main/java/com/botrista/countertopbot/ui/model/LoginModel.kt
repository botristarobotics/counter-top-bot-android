package com.botrista.countertopbot.ui.model

data class LoginModel(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val accessExpiresIn: Int
)
