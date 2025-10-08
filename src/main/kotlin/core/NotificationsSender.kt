package org.example.core

import convertEpochToLocal
import core.ports.driven.ForCreatingIssues.ForCreatingIssues
import core.ports.driving.ForReceivingCommands.ForReceivingCommands
import core.ports.driving.ForReceivingCommands.SummaryAndId
import org.example.core.ports.driven.ForOutputtingIssues.ForOutputtingIssues
import org.example.core.ports.driving.ForReceivingNotifications.ForProcessingNotifications
import org.example.core.ports.driving.ForReceivingNotifications.Issue

class NotificationsSender(
    private val youTrackClient: ForProcessingNotifications,
    private val youTrackIssueCreator: ForCreatingIssues,
    private val telegramNotificationAdapter: ForOutputtingIssues
) : ForReceivingCommands {
    private val seenNotificationIds = mutableSetOf<String>()

    suspend fun checkAndSendNewNotifications() {
        val issues = youTrackClient.getRecentNotifications()
        val newIssues = issues.filter { it.id !in seenNotificationIds }

        for (issue in newIssues) {
            val message = formatNotificationMessage(issue)
            telegramNotificationAdapter.sendIssue(message)
            seenNotificationIds.add(issue.id)
        }
    }

    override suspend fun processCommand(message: String): SummaryAndId? {
        val res = youTrackIssueCreator.createIssue(message) ?: return null
        return SummaryAndId(
            res.summary, res.idReadable
        )
    }

    private fun formatNotificationMessage(issue: Issue) =
        "*New Notification*\n" +
                "*ID*: `${issue.id}`\n" +
                "*Created at*: ${convertEpochToLocal(issue.created)}\n" +
                "*Summary*: ${issue.summary}\n" +
                "*Description*: ${issue.description}\n"
}
