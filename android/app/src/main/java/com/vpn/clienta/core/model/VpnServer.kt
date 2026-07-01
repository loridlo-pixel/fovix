package com.vpn.clienta.data.model

data class VpnServer(
    val name: String,
    val host: String,
    val port: Int,
    val uuid: String? = null,
    val raw: String = ""
)