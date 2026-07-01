package com.vpn.clienta.data.parser

import android.util.Base64
import com.vpn.clienta.core.model.VpnServer

object UnifiedParser {

    fun parse(lines: List<String>): List<VpnServer> {
        val result = mutableListOf<VpnServer>()

        lines.forEach { line ->

            when {
                line.startsWith("vless://") -> {
                    QrParser.parse(line)?.let { result.add(it) }
                }

                else -> {
                    runCatching {
                        val decoded = String(Base64.decode(line, Base64.DEFAULT))
                        decoded.lines().forEach { inner ->
                            QrParser.parse(inner)?.let { result.add(it) }
                        }
                    }
                }
            }
        }

        return result
    }
}