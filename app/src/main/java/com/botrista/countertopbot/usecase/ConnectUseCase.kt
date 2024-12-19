package com.botrista.countertopbot.usecase

import com.botrista.countertopbot.repository.agei.DeviceRepository

class ConnectUseCase(
    private val deviceRepository: DeviceRepository,
) {

    suspend fun execute(): Boolean {
        return deviceRepository.connect()
    }
}