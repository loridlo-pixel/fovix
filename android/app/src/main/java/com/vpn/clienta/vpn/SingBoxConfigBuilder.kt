package com.vpn.clienta.vpn

import org.json.JSONObject

object SingBoxConfigBuilder {

    fun build(host: String, port: Int, uuid: String): String {

        val root = JSONObject()

        val out = JSONObject()
        out.put("type", "vless")
        out.put("server", host)
        out.put("server_port", port)
        out.put("uuid", uuid)
        out.put("flow", "xtls-rprx-vision")

        root.put("outbounds", listOf(out))

        return root.toString(2)
    }
}