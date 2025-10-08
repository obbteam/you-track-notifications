package org.example.core.ports.driving.ForReceivingNotifications

interface ForProcessingNotifications {
    suspend fun getRecentNotifications() : List<Issue>
}