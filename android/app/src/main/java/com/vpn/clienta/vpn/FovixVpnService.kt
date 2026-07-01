package com.vpn.clienta.vpn

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.net.VpnService
import android.os.Build
import androidx.core.app.NotificationCompat
import com.vpn.clienta.core.model.VpnServer
import java.io.File

class FovixVpnService : VpnService() {

    private var process: Process? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val name = intent?.getStringExtra("name") ?: return START_NOT_STICKY
        val host = intent.getStringExtra("host") ?: return START_NOT_STICKY
        val port = intent.getIntExtra("port", 443)
        val uuid = intent.getStringExtra("uuid") ?: return START_NOT_STICKY

        startForeground(1001, createNotification(name))

        startSingBox(host, port, uuid)

        return START_STICKY
    }

    private fun startSingBox(host: String, port: Int, uuid: String) {

        val config = """
        {
          "log": { "level": "info" },
          "inbounds": [
            {
              "type": "tun",
              "inet4_address": "172.19.0.1/30",
              "auto_route": true,
              "strict_route": true
            }
          ],
          "outbounds": [
            {
              "type": "vless",
              "server": "$host",
              "server_port": $port,
              "uuid": "$uuid",
              "flow": "xtls-rprx-vision"
            }
          ]
        }
        """.trimIndent()

        val file = File(filesDir, "config.json")
        file.writeText(config)

        val binary = File(filesDir, "sing-box")

        if (!binary.exists()) {
            throw IllegalStateException("sing-box binary not installed")
        }

        process = ProcessBuilder(
            binary.absolutePath,
            "run",
            "-c",
            file.absolutePath
        ).start()
    }

    override fun onDestroy() {
        process?.destroy()
        super.onDestroy()
    }

    private fun createNotification(title: String): Notification {

        val channelId = "vpn"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "VPN",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Fovix VPN")
            .setContentText(title)
            .setSmallIcon(android.R.drawable.stat_sys_vpn_ic)
            .build()
    }
}