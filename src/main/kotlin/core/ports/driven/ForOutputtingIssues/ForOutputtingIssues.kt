package org.example.core.ports.driven.ForOutputtingIssues

interface ForOutputtingIssues {
    suspend fun sendIssue(message: String) : Boolean
}