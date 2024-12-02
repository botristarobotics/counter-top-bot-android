package com.botrista.countertopbot.remote.register


import com.google.gson.annotations.SerializedName

data class RegisterInfo(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val infoData: String,
    @SerializedName("status")
    val status: Boolean
)