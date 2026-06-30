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

    private var tunInterface: ParcelFileDescriptor? = null
    private var process: Process? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val host = intent?.getStringExtra("host") ?: return START_NOT_STICKY
        val port = intent.getIntExtra("port", 443)
        val uuid = intent.getStringExtra("uuid") ?: return START_NOT_STICKY

        val security = intent.getStringExtra("security")
        val flow = intent.getStringExtra("flow")
        val sni = intent.getStringExtra("sni")
        val pbk = intent.getStringExtra("pbk")
        val sid = intent.getStringExtra("sid")
        val fp = intent.getStringExtra("fp")
        val type = intent.getStringExtra("type")

        startForeground(1001, createNotification())

        startTunnel(
            host = host,
            port = port,
            uuid = uuid,
            security = security,
            flow = flow,
            sni = sni,
            pbk = pbk,
            sid = sid,
            fp = fp,
            type = type
        )

        return START_STICKY
    }

    private fun startTunnel(
        host: String,
        port: Int,
        uuid: String,
        security: String?,
        flow: String?,
        sni: String?,
        pbk: String?,
        sid: String?,
        fp: String?,
        type: String?
    ) {

        // ─────────────────────────────
        // 1. VPN TUN INTERFACE
        // ─────────────────────────────
        val builder = Builder()
            .setSession("Fovix VPN")
            .addAddress("172.19.0.2", 30)
            .addRoute("0.0.0.0", 0)
            .addDnsServer("1.1.1.1")
            .setMtu(1500)

        tunInterface = builder.establish()

        // ─────────────────────────────
        // 2. CONFIG FILE
        // ─────────────────────────────
        val configFile = File(filesDir, "config.json")

        val server = VlessServer(
            name = host,
            host = host,
            port = port,
            uuid = uuid,
            security = security,
            flow = flow,
            sni = sni,
            publicKey = pbk,
            shortId = sid,
            fingerprint = fp,
            network = type,
            raw = ""
        )

        configFile.writeText(
            SingBoxConfigBuilder.build(server)
        )

        // ─────────────────────────────
        // 3. SING-BOX BINARY
        // ─────────────────────────────
        val binary = File(filesDir, "sing-box")

        if (!binary.exists()) {
            throw IllegalStateException("sing-box binary not found in filesDir")
        }

        binary.setExecutable(true)

        // ─────────────────────────────
        // 4. START PROCESS
        // ─────────────────────────────
        process = ProcessBuilder(
            binary.absolutePath,
            "run",
            "-c",
            configFile.absolutePath
        )
            .redirectErrorStream(true)
            .start()

        // ─────────────────────────────
        // 5. LOG STREAM (DEBUG)
        // ─────────────────────────────
        Thread {
            process?.inputStream?.bufferedReader()?.forEachLine {
                android.util.Log.d("SINGBOX", it)
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            tunInterface?.close()
        } catch (_: Exception) {}

        try {
            process?.destroy()
        } catch (_: Exception) {}
    }

    private fun createNotification(): Notification {

        val channelId = "vpn_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Fovix VPN Service",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Fovix VPN running")
            .setContentText("Secure tunnel active")
            .setSmallIcon(android.R.drawable.stat_sys_vpn_ic)
            .setOngoing(true)
            .build()
    }
}