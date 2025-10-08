package org.example.core.ports.driving.ForReceivingCommands

interface ForReceivingCommands {
    suspend fun processCommand(message: String) : SummaryAndId?
}