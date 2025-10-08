package org.example.adapters.driving.ForRecevingNotifications

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class YouTrackNotification(
    val id: String,
    val created: Long,
    val idReadable: String,
    val summary: String? = null,
    val description: String? = null,
)