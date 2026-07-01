class FovixVpnService : VpnService() {

    private var process: Process? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val name = intent?.getStringExtra("name") ?: "VPN"
        val host = intent?.getStringExtra("host") ?: return START_NOT_STICKY
        val port = intent.getIntExtra("port", 443)
        val uuid = intent.getStringExtra("uuid") ?: ""

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

        val configFile = File(filesDir, "config.json").apply {
            writeText(config)
        }

        val binary = File(filesDir, "sing-box")

        if (!binary.exists()) {
            stopSelf()
            throw IllegalStateException("sing-box binary not installed")
        }

        process = ProcessBuilder(
            binary.absolutePath,
            "run",
            "-c",
            configFile.absolutePath
        )
            .redirectErrorStream(true)
            .start()
    }

    override fun onDestroy() {
        process?.destroy()
        process = null
        super.onDestroy()
    }
}