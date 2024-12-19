package com.botrista.countertopbot.repository.agei

interface DeviceRepository {
    suspend fun connect(): Boolean
    suspend fun disconnect(): Boolean
}