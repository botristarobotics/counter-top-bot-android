package com.botrista.countertopbot.usecase

import com.botrista.countertopbot.repository.hotpot.HotpotRepository
import com.botrista.countertopbot.repository.login.LoginRepository
import com.botrista.countertopbot.ui.model.HotpotModel

class HotpotUseCase(
    private val loginRepository: LoginRepository,
    private val hotpotRepository: HotpotRepository,
) {

    suspend fun execute(): Result<HotpotModel> {
        val accessToken = loginRepository.getAccessToken()
            ?: return Result.failure(Exception("Login failed"))
        return hotpotRepository.getHotpot(accessToken)
    }
}