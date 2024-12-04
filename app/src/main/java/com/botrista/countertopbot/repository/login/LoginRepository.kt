package com.botrista.countertopbot.repository.login

import com.botrista.countertopbot.ui.model.LoginModel

interface LoginRepository {

    suspend fun login(
        serialNum: String,
        signature: String,
        verify: String
    ): Result<LoginModel>

    fun getAccessToken(): String?
}