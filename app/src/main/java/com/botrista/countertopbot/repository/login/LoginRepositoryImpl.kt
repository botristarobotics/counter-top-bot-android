package com.botrista.countertopbot.repository.login

import android.content.SharedPreferences
import com.botrista.countertopbot.remote.login.LoginApiService
import com.botrista.countertopbot.remote.login.LoginRequest
import com.botrista.countertopbot.repository.BaseRepository
import com.botrista.countertopbot.ui.model.LoginModel

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val sharedPreferences: SharedPreferences
) : BaseRepository(), LoginRepository {

    override suspend fun login(
        serialNum: String,
        signature: String,
        verify: String
    ): Result<LoginModel> {
        return handleApiResponse(
            apiCall = {
                loginApiService.login(
                    LoginRequest(
                        serialNum = serialNum,
                        signature = signature,
                        verify = verify,
                    )
                )
            },
            mapper = { loginResponse ->
                val loginModel = LoginModel(
                    accessToken = loginResponse.data.accessToken,
                    refreshToken = loginResponse.data.refreshToken,
                    tokenType = loginResponse.data.tokenType,
                    accessExpiresIn = loginResponse.data.accessExpiresIn
                )
                saveAccessToken(loginModel.accessToken)
                saveRefreshToken(loginModel.refreshToken)
                loginModel
            }
        )
    }

    private fun saveAccessToken(token: String) {
        //TODO:Use constant for key
        sharedPreferences.edit().putString("access_token", token).apply()
    }

    private fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString("refresh_token", token).apply()
    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }
}