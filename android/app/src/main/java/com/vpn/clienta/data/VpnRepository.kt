package com.vpn.clienta.data

import com.vpn.clienta.core.model.VpnServer

class VpnRepository {

    private val servers = mutableListOf<VpnServer>()

    fun setServers(list: List<VpnServer>) {
        servers.clear()
        servers.addAll(list)
    }

    fun getServers(): List<VpnServer> = servers

    fun getByName(name: String): VpnServer? {
        return servers.find { it.name == name }
    }
}