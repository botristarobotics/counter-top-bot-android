package com.botrista.countertopbot.repository.register

import com.botrista.countertopbot.ui.model.RegisterModel
import com.botrista.countertopbot.util.Const

interface RegisterRepository {
    suspend fun register(
        serialNum: String,
        type: String = Const.TYPE_MACHINE,
        publicKey: String
    ): Result<RegisterModel>
}