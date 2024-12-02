package com.botrista.countertopbot.repository.register

import com.botrista.countertopbot.remote.register.RegisterApiService
import com.botrista.countertopbot.remote.register.RegisterInfo
import com.botrista.countertopbot.remote.register.RegisterRequest
import com.botrista.countertopbot.repository.BaseRepository
import kotlinx.coroutines.flow.Flow

class RegisterRepositoryImpl(private val registerApiService: RegisterApiService) :
    BaseRepository(), RegisterRepository {

    override fun register(
        serialNum: String,
        type: String ,
        publicKey: String
    ): Flow<Result<RegisterInfo>> {
        return handleApiResponse {
            registerApiService.register(
                RegisterRequest(
                    serialNum = serialNum,
                    type = type,
                    publicKey = publicKey,
                )
            )
        }
    }
}