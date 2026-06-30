package com.vpn.clienta.subscription

import com.vpn.clienta.parser.VlessParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class SubscriptionRepository {

    suspend fun loadFromUrl(url: String): List<`VlessServer.kt`> {
        return withContext(Dispatchers.IO) {

            val raw = URL(url).readText()

            // поддержка base64 подписок (как у VPN сервисов)
            val decoded = try {
                String(android.util.Base64.decode(raw, android.util.Base64.DEFAULT))
            } catch (e: Exception) {
                raw
            }

            VlessParser.parse(decoded)
        }
    }
}