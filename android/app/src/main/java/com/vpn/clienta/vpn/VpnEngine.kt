package com.vpn.clienta.vpn

import android.content.Context
import com.vpn.clienta.core.model.VpnServer

object VpnEngine {

    fun connect(context: Context, server: VpnServer) {
        // TODO: sing-box bridge
        // здесь будет запуск tunnel service
    }

    fun disconnect(context: Context) {
        // TODO: stop service
    }
}