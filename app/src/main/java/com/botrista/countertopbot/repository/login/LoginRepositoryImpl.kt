package com.botrista.countertopbot.repository.login

import com.botrista.countertopbot.remote.login.LoginApiService
import com.botrista.countertopbot.remote.login.LoginInfo
import com.botrista.countertopbot.remote.login.LoginRequest
import com.botrista.countertopbot.repository.BaseRepository
import com.botrista.countertopbot.util.KeyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val keyManager: KeyManager,
) : BaseRepository(), LoginRepository {

    override fun login(
        serialNum: String,
        timeStampInMillSec: Long,
    ): Flow<Result<LoginInfo>> {
        val msg = "$serialNum - $timeStampInMillSec"
        val signatureByteArray = keyManager.signEcdsa(msg.toByteArray(Charsets.UTF_8)) ?: return flow {
            emit(
                Result.failure(Exception("Sign failed"))
            )
        }
        val signatureString = String(signatureByteArray, Charsets.UTF_8)
        return handleApiResponse {
            loginApiService.login(
                LoginRequest(
                    serialNum = serialNum,
                    signature = signatureString,
                    verify = msg,
                )
            )
        }
    }
}