package com.botrista.countertopbot.repository.login

import com.botrista.countertopbot.remote.login.LoginInfo
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    fun login(
        serialNum: String,
        timeStampInMillSec: Long,
    ): Flow<Result<LoginInfo>>
}