package com.vpn.clienta.data

object VpnRepository {

    private val servers = mutableListOf<`VlessServer.kt`>()

    fun setServers(list: List<`VlessServer.kt`>) {
        servers.clear()
        servers.addAll(list)
    }

    fun getServers(): List<`VlessServer.kt`> = servers

    fun getByName(name: String): `VlessServer.kt`? {
        return servers.find { it.name == name }
    }
}