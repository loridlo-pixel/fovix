package com.vpn.clienta.vpn

import android.content.Context
import android.content.Intent
import com.vpn.clienta.core.model.VpnServer

object VpnEngine {

    lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun connect(server: VpnServer) {
        val intent = Intent(appContext, FovixVpnService::class.java).apply {
            putExtra("name", server.name)
            putExtra("host", server.host)
            putExtra("port", server.port)
            putExtra("uuid", server.uuid)
        }

        appContext.startService(intent)
    }

    fun disconnect() {
        val intent = Intent(appContext, FovixVpnService::class.java)
        appContext.stopService(intent)
    }
}