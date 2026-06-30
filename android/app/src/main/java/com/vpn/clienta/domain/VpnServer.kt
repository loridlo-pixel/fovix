package com.vpn.clienta.vpn

import android.app.*
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import androidx.core.app.NotificationCompat

class FovixVpnService : VpnService() {

    private var tun: ParcelFileDescriptor? = null
    private var process: Process? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(1001, createNotification())

        startTunAndSingbox()

        return START_STICKY
    }

    private fun startTunAndSingbox() {

        // 1. CREATE TUN
        val builder = Builder()
            .setSession("Fovix VPN")
            .addAddress("172.19.0.2", 30)
            .addRoute("0.0.0.0", 0)
            .setMtu(1500)

        tun = builder.establish()

        val tunFd = tun?.detachFd() ?: return

        // 2. INSTALL BINARY
        val binary = SingBoxBinaryInstaller.install(this)

        // 3. CONFIG
        val config = SingBoxConfigBuilder.buildTunConfig()

        val configFile = SingBoxController(this).apply {
            writeConfig(config)
        }

        // 4. START SING-BOX WITH TUN FD
        process = ProcessBuilder(
            binary.absolutePath,
            "run",
            "-c",
            configFileFilePath(),
            "--disable-color",
            "--log-level", "info"
        )
            .redirectErrorStream(true)
            .start()

        // ⚠️ CRITICAL: attach TUN fd to process
        attachTunToProcess(tunFd)
    }

    private fun attachTunToProcess(tunFd: Int) {
        try {
            // Android level trick: protect VPN service socket usage
            protect(tunFd)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun configFileFilePath(): String {
        return File(filesDir, "config.json").absolutePath
    }

    private fun createNotification(): Notification {

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
            .setContentText("TUN active")
            .setSmallIcon(android.R.drawable.stat_sys_vpn_ic)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()

        tun?.close()
        process?.destroy()
    }
}