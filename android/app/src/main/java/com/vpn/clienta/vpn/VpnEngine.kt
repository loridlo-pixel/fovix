package com.vpn.clienta.vpn

import android.content.Context
import android.content.Intent
import com.vpn.clienta.core.model.VpnServer

object VpnEngine {

    fun connect(context: Context, server: VpnServer) {
        val intent = Intent(context, FovixVpnService::class.java).apply {
            putExtra("name", server.name)
            putExtra("host", server.host)
            putExtra("port", server.port)
            putExtra("uuid", server.uuid)
        }
        context.startService(intent)
    }

    fun disconnect(context: Context) {
        context.stopService(Intent(context, FovixVpnService::class.java))
    }
}