package com.vpn.clienta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vpn.clienta.domain.VpnServer

@Composable
fun ServerCard(
    server: VpnServer,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) Color(0xFF1C2433) else Color(0xFF121826)
    val border = if (selected) Color(0xFF00E676) else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(bg, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = server.flag,
                fontSize = 22.sp
            )

            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                Text(
                    text = server.name,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = server.country,
                    color = Color(0xFF8A8F98),
                    fontSize = 12.sp
                )
            }

            Text(
                text = "${server.ping} ms",
                color = if (server.ping < 80) Color(0xFF00E676)
                else if (server.ping < 150) Color(0xFFFFC107)
                else Color(0xFFFF4D4D),
                fontSize = 12.sp
            )
        }
    }
}