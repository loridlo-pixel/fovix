package com.vpn.clienta.vpn

import android.app.Service
import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log

class FovixVpnService : VpnService() {

    private var vpnInterface: ParcelFileDescriptor? = null
    private var process: Process? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("FOVIX-VPN", "SERVICE STARTED")

        startVpn()

        return START_STICKY
    }

    private fun startVpn() {

        try {
            val builder = Builder()

            builder.setSession("FOVIX VPN")
                .addAddress("10.10.0.2", 32)
                .addRoute("0.0.0.0", 0)

            vpnInterface = builder.establish()

            Log.d("FOVIX-VPN", "TUN CREATED")

            // TODO: здесь будет запуск sing-box

        } catch (e: Exception) {
            Log.e("FOVIX-VPN", "ERROR", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        vpnInterface?.close()
        process?.destroy()

        Log.d("FOVIX-VPN", "STOPPED")
    }
}