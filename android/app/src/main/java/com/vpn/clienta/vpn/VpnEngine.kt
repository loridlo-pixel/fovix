package com.vpn.clienta.vpn

import android.content.Context

class VpnEngine(private val context: Context) {

    private val core = SingBoxController(context)

    fun start(config: String) {
        core.start(config)
    }

    fun stop() {
        core.stop()
    }
}