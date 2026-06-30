package com.vpn.clienta.parser

data class `VlessServer.kt`(
    val name: String,

    val host: String,
    val port: Int,
    val uuid: String,

    // security layer
    val security: String? = null,   // reality / tls / none
    val flow: String? = null,       // xtls-rprx-vision

    // reality params
    val sni: String? = null,
    val publicKey: String? = null,
    val shortId: String? = null,
    val fingerprint: String? = null,

    // transport
    val network: String? = "tcp",   // tcp/ws/grpc

    val raw: String
)