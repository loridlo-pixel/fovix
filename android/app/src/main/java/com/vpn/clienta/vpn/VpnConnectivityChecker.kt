package com.vpn.clienta.vpn

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object VpnConnectivityChecker {

    suspend fun isVpnActive(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://api.ipify.org")
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 4000
            conn.readTimeout = 4000

            val ip = conn.inputStream.bufferedReader().readText()

            // если IP получен — туннель вероятно работает
            ip.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}