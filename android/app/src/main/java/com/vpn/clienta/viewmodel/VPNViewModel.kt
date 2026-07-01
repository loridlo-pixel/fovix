package com.vpn.clienta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vpn.clienta.core.model.VpnServer
import com.vpn.clienta.ui.state.VpnUiState
import com.vpn.clienta.vpn.VpnEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VPNViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(VpnUiState())
    val state: StateFlow<VpnUiState> = _state.asStateFlow()

    fun setServers(list: List<VpnServer>) {
        _state.value = _state.value.copy(servers = list)
    }

    fun selectServer(server: VpnServer) {
        _state.value = _state.value.copy(selectedServer = server)
    }

    fun connect() {
        val server = _state.value.selectedServer ?: return
        VpnEngine.connect(getApplication(), server)
        _state.value = _state.value.copy(isConnected = true)
    }

    fun disconnect() {
        VpnEngine.disconnect(getApplication())
        _state.value = _state.value.copy(isConnected = false)
    }
}