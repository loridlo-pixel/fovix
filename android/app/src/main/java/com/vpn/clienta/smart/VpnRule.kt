package com.vpn.clienta.smart

enum class VpnRuleAction {
    ALWAYS_ON,
    ALWAYS_OFF,
    SMART
}

data class VpnRule(
    val domain: String,
    val action: VpnRuleAction
)