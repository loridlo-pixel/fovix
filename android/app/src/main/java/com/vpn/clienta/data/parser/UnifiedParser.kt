package com.vpn.clienta.data.parser

import android.util.Base64
import com.vpn.clienta.data.model.VpnServer

object UnifiedParser {

    fun parse(lines: List<String>): List<VpnServer> {

        val result = mutableListOf<VpnServer>()

        lines.forEach { line ->

            when {

                line.startsWith("vless://") -> {
                    QrParser.parse(line)?.let { result.add(it) }
                }

                // base64 list
                else -> {
                    runCatching {
                        val decoded = String(Base64.decode(line, Base64.DEFAULT))
                        decoded.lines().forEach {
                            QrParser.parse(it)?.let { s -> result.add(s) }
                        }
                    }
                }
            }
        }

        return result
    }
}