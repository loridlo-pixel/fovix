package com.vpn.clienta.subscription

import okhttp3.OkHttpClient
import okhttp3.Request

class SubscriptionRepository {

    private val client = OkHttpClient()

    fun fetchSubscription(url: String): String? {

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            return response.body?.string()
        }
    }
}