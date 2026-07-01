package com.vpn.clienta.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class SubscriptionLoader {

    private val client = OkHttpClient()

    suspend fun load(url: String): List<String> {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            response.body?.string()?.lines() ?: emptyList()
        }
    }
}