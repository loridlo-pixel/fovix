package com.vpn.clienta.core.model

data class VpnServer(
    val name: String,
    val host: String,
    val port: Int,
    val uuid: String,
    val protocol: String = "vless",

    val security: String? = null,
    val flow: String? = null,
    val sni: String? = null,
    val publicKey: String? = null,
    val shortId: String? = null,
    val fingerprint: String? = null,
    val network: String? = null
)