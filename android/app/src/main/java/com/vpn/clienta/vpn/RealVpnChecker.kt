package com.vpn.clienta.vpn

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object RealVpnChecker {

    suspend fun check(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {

            // 1. быстрый endpoint (минимальная задержка)
            val url = URL("https://api.ipify.org?format=text")

            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 4000
            conn.readTimeout = 4000

            val ip = conn.inputStream.bufferedReader().readText().trim()

            // 2. защита от пустого ответа
            ip.isNotEmpty() && ip.count { it == '.' } == 3

        } catch (e: Exception) {
            false
        }
    }
}