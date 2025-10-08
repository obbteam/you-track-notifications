package org.example

import TelegramNotificationAdapter
import adapters.driven.ForCreatingIssues.YouTrackIssueCreator
import core.ports.driven.ForCreatingIssues.ForCreatingIssues
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.*
import loadConfig
import org.example.adapters.driving.ForRecevingNotifications.YouTrackClient
import org.example.core.NotificationsSender
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.example.adapters.driving.ForRecevingCommands.TelegramCommandAdapter
import org.example.core.ports.driven.ForOutputtingIssues.ForOutputtingIssues
import org.example.core.ports.driving.ForReceivingNotifications.ForProcessingNotifications
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


fun main() = runBlocking {
    val config = loadConfig()
    val httpClient = HttpClient(CIO.create()) {
        install(ContentNegotiation) {
            json()
        }
    }

    val youTrackAdapter: ForProcessingNotifications =
        YouTrackClient(httpClient, config.youtrackUrl, config.youtrackToken)
    val youTrackIssueCreator: ForCreatingIssues =
        YouTrackIssueCreator(httpClient, config.youtrackUrl, config.youtrackToken, config.youtrackProjectId)
    val telegramCommandAdapter = TelegramCommandAdapter(
        token = config.telegramBotToken,
        botUsername = config.telegramBotUserName,
    )

    val telegramNotificationAdapter: ForOutputtingIssues =
        TelegramNotificationAdapter(telegramCommandAdapter, config.telegramChatId)

    val notificationsSender = NotificationsSender(
        youTrackClient = youTrackAdapter,
        youTrackIssueCreator = youTrackIssueCreator,
        telegramNotificationAdapter
    )

    telegramCommandAdapter.setCommandReceiver(notificationsSender)

    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    botsApi.registerBot(telegramCommandAdapter)

    // check new issues every 30 seconds
    launch {
        while (true) {
            println("Checking for notifications...")
            notificationsSender.checkAndSendNewNotifications()
            delay(30_000)
        }
    }
    println("Bot started successfully!")
}