package com.botrista.countertopbot.repository.login

import com.botrista.countertopbot.local.LocalStorage
import com.botrista.countertopbot.remote.login.LoginApiService
import com.botrista.countertopbot.remote.login.LoginRequest
import com.botrista.countertopbot.repository.BaseRepository
import com.botrista.countertopbot.ui.model.LoginModel

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val localStorage: LocalStorage,
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
                localStorage.saveAccessToken(loginModel.accessToken)
                localStorage.saveRefreshToken(loginModel.refreshToken)
                loginModel
            }
        )
    }

    override fun getAccessToken(): String? {
        return localStorage.getAccessToken()
    }
}