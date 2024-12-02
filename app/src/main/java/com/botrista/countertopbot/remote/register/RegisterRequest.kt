package com.botrista.countertopbot.remote.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("serial_num")
    val serialNum: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("public_key")
    val publicKey: String,
)
