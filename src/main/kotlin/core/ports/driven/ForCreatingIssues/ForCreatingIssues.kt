package org.example.core.ports.driven.ForCreatingIssues

interface ForCreatingIssues {
    suspend fun createIssue(summary: String) : SummaryAndId?
}