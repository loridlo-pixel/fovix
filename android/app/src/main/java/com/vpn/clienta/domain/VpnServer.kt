package com.vpn.clienta.domain

data class VpnServer(
    val name: String,
    val country: String,
    val flag: String,
    val ping: Int
)