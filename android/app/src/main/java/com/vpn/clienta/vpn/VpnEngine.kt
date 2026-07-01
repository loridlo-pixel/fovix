package com.vpn.clienta.vpn

import android.content.Intent
import androidx.core.content.ContextCompat
import com.vpn.clienta.AppContext
import com.vpn.clienta.parser.VlessServer

object VpnEngine {

    fun connect(server: VlessServer) {

        val ctx = AppContext.get()

        val intent = Intent(ctx, FovixVpnService::class.java).apply {
            putExtra("host", server.host)
            putExtra("port", server.port)
            putExtra("uuid", server.uuid)
        }

        ContextCompat.startForegroundService(ctx, intent)
    }

    fun disconnect() {
        val ctx = AppContext.get()
        val intent = Intent(ctx, FovixVpnService::class.java)
        ctx.stopService(intent)
    }
}