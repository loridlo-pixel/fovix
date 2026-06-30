package com.vpn.clienta.smart

object DomainClassifier {

    fun classify(url: String): String {

        return when {
            url.contains("google") -> "trusted"
            url.contains("bank") -> "finance"
            url.contains("instagram") -> "social"
            url.contains("youtube") -> "media"
            else -> "unknown"
        }
    }
}