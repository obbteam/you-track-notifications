package org.example.core.ports.driving.ForReceivingNotifications

data class Issue(
    val id: String,
    val created: Long,
    val idReadable: String,
    val summary: String? = null,
    val description: String? = null,
)