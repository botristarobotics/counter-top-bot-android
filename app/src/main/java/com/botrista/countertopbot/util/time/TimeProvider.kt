package com.botrista.countertopbot.util.time

interface TimeProvider {
    fun getCurrentTimeMillis(): Long
}