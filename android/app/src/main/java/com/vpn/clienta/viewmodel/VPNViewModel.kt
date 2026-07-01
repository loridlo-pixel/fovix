package com.vpn.clienta.viewmodel

import androidx.lifecycle.ViewModel
import com.vpn.clienta.core.model.VpnServer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VPNViewModel : ViewModel() {

    private val _servers = MutableStateFlow<List<VpnServer>>(emptyList())
    val servers: StateFlow<List<VpnServer>> = _servers

    private val _selectedServer = MutableStateFlow<VpnServer?>(null)
    val selectedServer: StateFlow<VpnServer?> = _selectedServer

    private val _vpnState = MutableStateFlow("DISCONNECTED")
    val vpnState: StateFlow<String> = _vpnState

    fun setServers(list: List<VpnServer>) {
        _servers.value = list
    }

    fun selectServer(server: VpnServer) {
        _selectedServer.value = server
    }

    fun setState(state: String) {
        _vpnState.value = state
    }
}