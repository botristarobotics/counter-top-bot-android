package com.botrista.countertopbot.repository.hotpot

import com.botrista.countertopbot.ui.model.HotpotModel

interface HotpotRepository {
    suspend fun getHotpot(token: String): Result<HotpotModel>
}