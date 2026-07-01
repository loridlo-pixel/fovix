package com.vpn.clienta.core.model

data class VpnServer(
    val name: String,
    val host: String,
    val port: Int,
    val uuid: String = "",
    val raw: String = ""
)