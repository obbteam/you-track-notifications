import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.core.ports.driven.ForOutputtingIssues.ForOutputtingIssues
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

// This class ONLY sends messages. It doesn't listen for updates.
class TelegramNotificationAdapter(
    private val sender: AbsSender,
    private val chatId: String
) : ForOutputtingIssues {

    override suspend fun sendIssue(message: String): Boolean {
        return try {
            val sendMessage = SendMessage(chatId, message).apply {
                enableMarkdown(true)
            }
            withContext(Dispatchers.IO) {
                sender.execute(sendMessage)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}