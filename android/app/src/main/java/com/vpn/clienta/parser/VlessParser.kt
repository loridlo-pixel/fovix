package com.vpn.clienta.parser

import android.net.Uri

object VlessParser {

    fun parse(uri: String): VlessServer {

        val cleaned = uri.removePrefix("vless://")

        val parts = cleaned.split("@")
        val uuid = parts[0]

        val hostPart = parts[1].split("?")
        val hostPort = hostPart[0].split(":")

        val host = hostPort[0]
        val port = hostPort.getOrElse(1) { "443" }.toInt()

        val query = Uri.parse(uri)

        return VlessServer(
            name = host,
            host = host,
            port = port,
            uuid = uuid,

            security = query.getQueryParameter("security"),
            flow = query.getQueryParameter("flow"),
            sni = query.getQueryParameter("sni"),
            publicKey = query.getQueryParameter("pbk"),
            shortId = query.getQueryParameter("sid"),
            fingerprint = query.getQueryParameter("fp"),
            network = query.getQueryParameter("type"),

            raw = uri
        )
    }
}