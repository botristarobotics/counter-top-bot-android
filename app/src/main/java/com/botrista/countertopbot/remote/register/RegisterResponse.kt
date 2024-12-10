package com.botrista.countertopbot.remote.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("machine_id")
    val machineId: String,
    @SerializedName("serial_num")
    val serialNum: String,
    @SerializedName("public_key")
    val publicKey: String,
    @SerializedName("_id")
    val id: String,
)