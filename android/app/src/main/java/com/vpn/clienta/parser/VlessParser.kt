package com.vpn.clienta.parser

object VlessParser {

    fun parse(text: String): List<VlessServer> {

        val lines = text.split("\n")

        val result = mutableListOf<VlessServer>()

        for (line in lines) {

            if (!line.startsWith("vless://")) continue

            val clean = line.substringAfter("vless://")

            val parts = clean.split("@")
            if (parts.size != 2) continue

            val right = parts[1]

            val hostPort = right.substringBefore("?")

            val host = hostPort.substringBefore(":")
            val port = hostPort.substringAfter(":").toIntOrNull() ?: 443

            val name = line.substringAfter("#", host)

            result.add(
                VlessServer(
                    name = name,
                    host = host,
                    port = port,
                    raw = line
                )
            )
        }

        return result
    }
}