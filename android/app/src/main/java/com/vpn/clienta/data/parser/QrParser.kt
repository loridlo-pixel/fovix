package com.vpn.clienta.data.parser

import com.vpn.clienta.data.model.VpnServer

object QrParser {

    fun parse(text: String): VpnServer? {
        if (!text.startsWith("vless://")) return null

        val noScheme = text.removePrefix("vless://")

        val parts = noScheme.split("@")
        if (parts.size < 2) return null

        val uuid = parts[0]
        val afterAt = parts[1]

        val name = if (afterAt.contains("#"))
            afterAt.substringAfter("#")
        else
            "QR Server"

        val hostPort = afterAt.substringBefore("?")
        val host = hostPort.substringBefore(":")
        val port = hostPort.substringAfter(":").toIntOrNull() ?: 443

        return VpnServer(
            name = name,
            host = host,
            port = port,
            uuid = uuid,
            raw = text
        )
    }
}