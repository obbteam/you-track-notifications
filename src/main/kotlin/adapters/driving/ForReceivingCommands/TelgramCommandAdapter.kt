package org.example.adapters.driving.ForRecevingCommands

import org.example.core.ports.driving.ForReceivingCommands.ForReceivingCommands
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TelegramCommandAdapter(
    token: String,
    private val botUsername: String,
) : TelegramLongPollingBot(token) {

    private lateinit var commandReceiver: ForReceivingCommands

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun setCommandReceiver(receiver: ForReceivingCommands) {
        this.commandReceiver = receiver
    }

    override fun getBotUsername(): String = botUsername

    override fun onUpdateReceived(update: Update) {
        if (!::commandReceiver.isInitialized) {
            println("Warning: commandReceiver has not been initialized yet.")
            return
        }
        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId.toString()

            when {
                messageText.startsWith("/create ") -> {
                    val summary = messageText.removePrefix("/create ").trim()
                    if (summary.isBlank()) {
                        sendMessageToChat(chatId, "Summary can not be null!")
                    } else {
                        sendMessageToChat(chatId, "Creating issue...")

                        coroutineScope.launch {
                            val res = commandReceiver.processCommand(summary)

                            if (res == null) {
                                sendMessageToChat(chatId, "Couldn't create an issue :///")
                            } else {
                                sendMessageToChat(chatId, "Issue created with summary: ${res.summary} amd id: ${res.idReadable}")
                            }
                        }
                    }
                }

                messageText == "/start" -> {
                    val welcomeMessage =
                        "Welcome to the YouTrack Bot! Here you can receive notifications for new issues or Use /createIssue <summary> to create an issue."
                    sendMessageToChat(chatId, welcomeMessage)
                }

                else -> {
                    sendMessageToChat(chatId, "Unknown command. Use /start to see commands.")
                }
            }
        }
    }

    private fun sendMessageToChat(chatId: String, message: String): Boolean {
        return try {
            val sendMessage = SendMessage(chatId, message).apply {
                enableMarkdown(true)
            }
            execute(sendMessage)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

