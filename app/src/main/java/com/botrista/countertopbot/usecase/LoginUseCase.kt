package com.botrista.countertopbot.usecase

import com.botrista.countertopbot.repository.exception.ForbiddenException
import com.botrista.countertopbot.repository.login.LoginRepository
import com.botrista.countertopbot.repository.register.RegisterRepository
import com.botrista.countertopbot.util.key.KeyManager
import com.botrista.countertopbot.util.time.TimeProvider
import java.security.PrivateKey
import java.security.PublicKey

class LoginUseCase(
    private val registerRepository: RegisterRepository,
    private val loginRepository: LoginRepository,
    private val keyManager: KeyManager<PublicKey, PrivateKey>,
    private val timeProvider: TimeProvider,
) {
    suspend fun execute(serialNum: String): Result<Unit> {
        keyManager.genAndSaveECKeyPair()
        if (keyManager.getPublicKey() == null) {
            return Result.failure(Exception("Key pair generation failed"))
        }

        val msg = getMsg(serialNum)
        val signature = getSignature(msg)

        val loginResult = loginRepository.login(serialNum, signature, msg)
        if (loginResult.isSuccess) {
            return Result.success(Unit)
        } else {
            if (loginResult.exceptionOrNull() is ForbiddenException) {
                val publicKey = keyManager.getPublicKeyPemFormat()
                if (publicKey != null) {
                    val registerResult =
                        registerRepository.register(serialNum, publicKey = publicKey)
                    if (registerResult.isSuccess) {
                        val retryMsg = getMsg(serialNum)
                        val retrySignature = getSignature(retryMsg)
                        val retryLoginResult =
                            loginRepository.login(serialNum, retrySignature, retryMsg)
                        if (retryLoginResult.isSuccess) {
                            return Result.success(Unit)
                        }
                    }
                }
            }
            //TODO: Handle other exceptions
            return Result.failure(
                loginResult.exceptionOrNull() ?: Exception("Unknown error")
            )
        }
    }

    private fun getMsg(serialNum: String): String {
        return "$serialNum-${timeProvider.getCurrentTimeMillis()}"
    }

    private fun getSignature(msg: String): String {
        return keyManager.signEcdsa(msg)
    }
}