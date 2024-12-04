package com.botrista.countertopbot.remote.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val infoData: String,
    @SerializedName("status")
    val status: Boolean
)