package com.vpn.clienta.core.parser

import com.vpn.clienta.core.model.VpnServer

object VpnParser {

    fun parseVless(uri: String): VpnServer {
        val cleaned = uri.removePrefix("vless://")

        val parts = cleaned.split("@")
        val uuid = parts[0]

        val hostPart = parts[1].split("?")[0]
        val host = hostPart.split(":")[0]
        val port = hostPart.split(":")[1].toInt()

        return VpnServer(
            name = "VLESS-$host",
            host = host,
            port = port,
            uuid = uuid
        )
    }
}