package com.vpn.clienta.parser

data class VlessServer(
    val name: String,
    val host: String,
    val port: Int,
    val uuid: String,
    val raw: String = ""
)