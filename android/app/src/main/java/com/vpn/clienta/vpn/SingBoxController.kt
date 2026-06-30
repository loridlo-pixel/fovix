package com.vpn.clienta.vpn

import org.json.JSONArray
import org.json.JSONObject

object SingBoxConfigBuilder {

    fun build(server: `VlessServer.kt`): String {

        val config = JSONObject()

        // INBOUND (TUN)
        val inbound = JSONObject()
        inbound.put("type", "tun")
        inbound.put("interface_name", "tun0")
        inbound.put("inet4_address", "172.19.0.1/30")
        inbound.put("auto_route", true)
        inbound.put("strict_route", true)

        // OUTBOUND (VLESS)
        val outbound = JSONObject()
        outbound.put("type", "vless")
        outbound.put("server", server.host)
        outbound.put("server_port", server.port)
        outbound.put("uuid", server.uuid)
        outbound.put("flow", "xtls-rprx-vision")

        val outbounds = JSONArray()
        outbounds.put(outbound)

        val inbounds = JSONArray()
        inbounds.put(inbound)

        config.put("inbounds", inbounds)
        config.put("outbounds", outbounds)

        return config.toString(2)
    }
}