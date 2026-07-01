package com.vpn.clienta.subscription

import com.vpn.clienta.core.model.VpnServer

class SubscriptionRepository {

    suspend fun loadFromUrl(url: String): List<VpnServer> {
        // TODO: parse subscription (base64 / url)
        // пока заглушка безопасная для CI

        return emptyList()
    }
}