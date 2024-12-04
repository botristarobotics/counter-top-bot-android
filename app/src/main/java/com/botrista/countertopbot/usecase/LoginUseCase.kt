package com.botrista.countertopbot.usecase

import com.botrista.countertopbot.repository.exception.ForbiddenException
import com.botrista.countertopbot.repository.login.LoginRepository
import com.botrista.countertopbot.repository.register.RegisterRepository
import com.botrista.countertopbot.util.KeyManager

class LoginUseCase(
    private val registerRepository: RegisterRepository,
    private val loginRepository: LoginRepository,
    private val keyManager: KeyManager,
) {
    suspend fun execute(serialNum: String): Result<Unit> {
        keyManager.genAndSaveECKeyPair()
        if (keyManager.loadPublicKey() == null) {
            return Result.failure(Exception("Key pair generation failed"))
        }

        val timeMillis = System.currentTimeMillis() //TODO: Use interface to get
        val msg = "$serialNum - $timeMillis"

        val signatureByteArray =
            keyManager.signEcdsa(msg.toByteArray(Charsets.UTF_8))
                ?: return Result.failure(Exception("Sign failed"))
        val signatureString = String(signatureByteArray, Charsets.UTF_8)

        val loginResult = loginRepository.login(serialNum, signatureString, msg)
        if (loginResult.isSuccess) {
            return Result.success(Unit)
        } else {
            if (loginResult.exceptionOrNull() is ForbiddenException) {
                val publicKey = keyManager.loadPublicKey()?.publicKeyAsHex
                if (publicKey != null) {
                    val registerResult =
                        registerRepository.register(serialNum, publicKey = publicKey)
                    if (registerResult.isSuccess) {
                        val retryLoginResult =
                            loginRepository.login(serialNum, signatureString, msg)
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


}