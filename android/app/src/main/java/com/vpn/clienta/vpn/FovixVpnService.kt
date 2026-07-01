package com.vpn.clienta.vpn

import android.app.*
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import androidx.core.app.NotificationCompat
import com.vpn.clienta.parser.VlessServer
import java.io.File

class FovixVpnService : VpnService() {

    private var tun: ParcelFileDescriptor? = null
    private var process: Process? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val host = intent?.getStringExtra("host") ?: return START_NOT_STICKY
        val port = intent.getIntExtra("port", 443)
        val uuid = intent.getStringExtra("uuid") ?: return START_NOT_STICKY

        startForeground(1, buildNotification())
        startTunnel(host, port, uuid)

        return START_STICKY
    }

    private fun startTunnel(host: String, port: Int, uuid: String) {

        val builder = Builder()
            .setSession("Fovix")
            .addAddress("172.19.0.2", 30)
            .addRoute("0.0.0.0", 0)
            .addDnsServer("1.1.1.1")

        tun = builder.establish()

        val config = SingBoxConfigBuilder.build(host, port, uuid)
        val file = File(filesDir, "config.json")
        file.writeText(config)

        val binary = File(filesDir, "sing-box")

        process = ProcessBuilder(binary.absolutePath, "run", "-c", file.absolutePath)
            .redirectErrorStream(true)
            .start()
    }

    override fun onDestroy() {
        tun?.close()
        process?.destroy()
        super.onDestroy()
    }

    private fun buildNotification(): Notification {

        val channelId = "vpn"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(channelId, "VPN", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(ch)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Fovix VPN")
            .setContentText("Connected")
            .setSmallIcon(android.R.drawable.stat_sys_vpn_ic)
            .setOngoing(true)
            .build()
    }
}