package com.vpn.clienta.vpn

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.vpn.clienta.core.model.VpnServer

object VpnEngine {

    fun connect(context: Context, server: VpnServer) {

        val intent = Intent(context, FovixVpnService::class.java).apply {
            putExtra("name", server.name)
            putExtra("host", server.host)
            putExtra("port", server.port)
            putExtra("uuid", server.uuid)
        }

        ContextCompat.startForegroundService(context, intent)
    }

    fun disconnect(context: Context) {
        val intent = Intent(context, FovixVpnService::class.java)
        context.stopService(intent)
    }
}