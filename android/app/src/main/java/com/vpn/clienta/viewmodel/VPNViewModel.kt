package com.vpn.clienta.viewmodel

import androidx.lifecycle.ViewModel
import com.vpn.clienta.core.model.VpnServer
import com.vpn.clienta.ui.state.VpnUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VPNViewModel : ViewModel() {

    private val _state = MutableStateFlow(VpnUiState())
    val state: StateFlow<VpnUiState> = _state.asStateFlow()

    fun setServers(list: List<VpnServer>) {
        _state.value = _state.value.copy(servers = list)
    }

    fun selectServer(server: VpnServer) {
        _state.value = _state.value.copy(selectedServer = server)
    }

    fun connect() {
        _state.value = _state.value.copy(isConnected = true)
    }

    fun disconnect() {
        _state.value = _state.value.copy(isConnected = false)
    }
}