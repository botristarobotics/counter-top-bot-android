package com.botrista.countertopbot.repository.hotpot

import com.botrista.countertopbot.remote.hotpot.HotpotApiService
import com.botrista.countertopbot.repository.BaseRepository
import com.botrista.countertopbot.ui.model.HotpotModel

class HotpotRepositoryImpl(
    private val hotpotApiService: HotpotApiService
) : BaseRepository(), HotpotRepository {
    override suspend fun getHotpot(token: String): Result<HotpotModel> {
        return handleApiResponse(
            apiCall = {
                hotpotApiService.getHotpot(token)
            },
            mapper = { hotpotResponse ->
                HotpotModel(
                    200,
                )
            }
        )
    }
}