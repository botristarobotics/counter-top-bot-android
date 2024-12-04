package com.botrista.countertopbot.remote.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Boolean
) {
    data class Data(
        @SerializedName("accessExpiresIn")
        val accessExpiresIn: Int,
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("refreshToken")
        val refreshToken: String,
        @SerializedName("tokenType")
        val tokenType: String
    )
}