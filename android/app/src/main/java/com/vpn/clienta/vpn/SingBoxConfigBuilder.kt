package com.vpn.clienta.vpn

import com.vpn.clienta.parser.VlessServer
import org.json.JSONArray
import org.json.JSONObject

object SingBoxConfigBuilder {

    fun build(server: VlessServer): String {

        val config = JSONObject()

        // ───── LOG ─────
        config.put("log", JSONObject().apply {
            put("level", "info")
        })

        // ───── INBOUND (TUN) ─────
        val inbound = JSONObject()
            .put("type", "tun")
            .put("interface_name", "tun0")
            .put("inet4_address", "172.19.0.1/30")
            .put("auto_route", true)
            .put("strict_route", true)

        config.put("inbounds", JSONArray().put(inbound))

        // ───── OUTBOUND (VLESS) ─────
        val vless = JSONObject()
            .put("type", "vless")
            .put("server", server.host)
            .put("server_port", server.port)
            .put("uuid", server.uuid)
            .put("flow", server.flow ?: "xtls-rprx-vision")

        // TLS / REALITY
        if (server.security == "reality") {

            val reality = JSONObject()
                .put("public_key", server.publicKey)
                .put("short_id", server.shortId)
                .put("server_name", server.sni)
                .put("fingerprint", server.fingerprint ?: "chrome")

            vless.put("tls", JSONObject().apply {
                put("enabled", true)
                put("server_name", server.sni)
                put("reality", reality)
            })
        }

        val outbound = JSONObject()
            .put("type", "selector")
            .put("tag", "proxy")
            .put("outbounds", JSONArray().put(vless))

        config.put("outbounds", JSONArray().put(outbound))

        // ───── ROUTE ─────
        val route = JSONObject()
            .put("final", "proxy")

        config.put("route", route)

        // ───── DNS ─────
        config.put("dns", JSONObject()
            .put("servers", JSONArray()
                .put("1.1.1.1")
                .put("8.8.8.8")
            )
        )

        return config.toString(2)
    }
}