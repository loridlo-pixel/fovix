package com.vpn.clienta.viewmodel

import androidx.lifecycle.ViewModel
import com.vpn.clienta.domain.VPNState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VPNViewModel : ViewModel() {

    private val _vpnState = MutableStateFlow(VPNState.Disconnected)
    val vpnState: StateFlow<VPNState> = _vpnState.asStateFlow()

    fun onConnectClick() {
        // Placeholder — VPN connection will be implemented later.
    }
}
