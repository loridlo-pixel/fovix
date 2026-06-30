package com.vpn.clienta.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpn.clienta.domain.VPNState
import com.vpn.clienta.vpn.FovixVpnService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VPNViewModel : ViewModel() {

    private val _vpnState = MutableStateFlow(VPNState.Disconnected)
    val vpnState: StateFlow<VPNState> = _vpnState

    fun onConnectClick(context: Context) {
        when (_vpnState.value) {
            VPNState.Disconnected -> connect(context)
            VPNState.Connected -> disconnect(context)
            VPNState.Connecting -> return
        }
    }

    private fun connect(context: Context) {
        viewModelScope.launch {
            _vpnState.value = VPNState.Connecting

            kotlinx.coroutines.delay(600)

            val intent = Intent(context, FovixVpnService::class.java)
            ContextCompat.startForegroundService(context, intent)

            _vpnState.value = VPNState.Connected
        }
    }

    private fun disconnect(context: Context) {
        val intent = Intent(context, FovixVpnService::class.java)
        context.stopService(intent)

        _vpnState.value = VPNState.Disconnected
    }
}