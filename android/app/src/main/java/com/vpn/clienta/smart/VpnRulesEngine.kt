package com.vpn.clienta.smart

class VpnRulesEngine(
    private val rules: List<VpnRule>
) {

    fun shouldEnableVpn(domain: String): Boolean {

        val rule = rules.find { domain.contains(it.domain) }

        return when (rule?.action) {

            VpnRuleAction.ALWAYS_ON -> true

            VpnRuleAction.ALWAYS_OFF -> false

            VpnRuleAction.SMART -> smartDecision(domain)

            null -> defaultSmart(domain)
        }
    }

    private fun smartDecision(domain: String): Boolean {
        return domain in listOf("finance", "social", "media")
    }

    private fun defaultSmart(domain: String): Boolean {
        return domain != "trusted"
    }
}