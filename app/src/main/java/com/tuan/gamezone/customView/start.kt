package com.tuan.gamezone.customView

fun PulseCountDown.start(callback: () -> Unit = {}) = start(object : OnCountdownCompleted {
    override fun completed() {
        callback()
    }
})