package com.botrista.countertopbot.remote.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("serial_num")
    val serialNum: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("verify")
    val verify: String
)