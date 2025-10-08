package org.example.adapters.driven.ForCreatingIssues

import org.example.core.ports.driven.ForCreatingIssues.ForCreatingIssues
import org.example.core.ports.driven.ForCreatingIssues.SummaryAndId
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class YouTrackIssueCreator(
    private val webClient: HttpClient,
    private val youtrackUrl: String,
    private val youtrackToken: String,
    private val projectId: String
) : ForCreatingIssues {

    companion object {
        const val ISSUES_ENDPOINT = "/api/issues"
    }

    override suspend fun createIssue(summary: String): SummaryAndId? {
        return try {
            val requestBody = CreateIssueRequest(
                project = ProjectIdentifier(id = projectId),
                summary = summary
            )

            val response : CreatedIssue = webClient.post(youtrackUrl + ISSUES_ENDPOINT) {
                bearerAuth(youtrackToken)
                contentType(ContentType.Application.Json)
                setBody(requestBody)
                parameter("fields", "idReadable,summary")
            }.body()

            println("Successfully created issue: ${response.idReadable} - ${response.summary}")

            SummaryAndId(response.summary, response.idReadable)
        } catch (e: Exception) {
            System.err.println("Failed to create YouTrack issue: ${e.stackTraceToString()}")
            null
        }
    }
}