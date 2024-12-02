package com.botrista.countertopbot.repository.register

import com.botrista.countertopbot.remote.register.RegisterInfo
import com.botrista.countertopbot.util.Const
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(
        serialNum: String,
        type: String = Const.TYPE_MACHINE,
        publicKey: String
    ): Flow<Result<RegisterInfo>>
}