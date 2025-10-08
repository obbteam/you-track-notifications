package org.example.adapters.driving.ForRecevingNotifications

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.core.ports.driving.ForReceivingNotifications.ForProcessingNotifications
import org.example.core.ports.driving.ForReceivingNotifications.Issue
import kotlin.String

class YouTrackClient(
    private val webClient: HttpClient,
    private val youtrackUrl: String,
    private val youtrackToken: String
) : ForProcessingNotifications {

    companion object {
        const val ISSUES_ENDPOINT = "/api/issues"
    }

    // Implementation to fetch recent notifications from YouTrack
    override suspend fun getRecentNotifications(): List<Issue> {
        return try {
            val response: List<YouTrackNotification> = webClient.get(youtrackUrl + ISSUES_ENDPOINT) {
                bearerAuth(youtrackToken)
                contentType(ContentType.Application.Json)

                parameter("`$`top", 10)
                parameter(
                    "fields",
                    "id,created,idReadable,summary,description"
                )
            }.body()

            response
                .map {
                    Issue(
                        it.id,
                        it.created,
                        it.idReadable,
                        it.summary,
                        it.description,
                    )
                }

        } catch (e: Exception) {
            System.err.println("Failed to fetch YouTrack notifications: ${e.stackTraceToString()}")
            return emptyList()
        }
    }
}