package com.vpn.clienta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vpn.clienta.ui.navigation.FexiNavHost
import com.vpn.clienta.ui.theme.VPNClientATheme
import com.vpn.clienta.viewmodel.VPNViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VPNClientATheme {

                val vm: VPNViewModel = viewModel()

                FexiNavHost(
                    viewModel = vm
                )
            }
        }
    }
}