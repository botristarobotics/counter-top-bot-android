package com.botrista.countertopbot.util.time

class TimeProviderImpl : TimeProvider {
    override fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}