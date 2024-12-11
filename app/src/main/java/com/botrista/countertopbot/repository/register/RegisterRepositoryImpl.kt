package com.botrista.countertopbot.repository.register

import com.botrista.countertopbot.remote.register.RegisterApiService
import com.botrista.countertopbot.remote.register.RegisterRequest
import com.botrista.countertopbot.repository.BaseRepository
import com.botrista.countertopbot.ui.model.RegisterModel

class RegisterRepositoryImpl(private val registerApiService: RegisterApiService) :
    BaseRepository(), RegisterRepository {

    override suspend fun register(
        serialNum: String,
        type: String,
        publicKey: String
    ): Result<RegisterModel> {
        return handleApiResponse(
            apiCall = {
                registerApiService.register(
                    RegisterRequest(
                        serialNum = serialNum,
                        type = type,
                        publicKey = publicKey,
                    )
                )
            },
            mapper = { //TODO: map to RegisterModel
                RegisterModel()
            }
        )
    }

}