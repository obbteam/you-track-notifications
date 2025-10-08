import java.util.Properties
import javax.print.attribute.standard.RequestingUserName

data class Config(
    val youtrackUrl: String,
    val youtrackToken: String,
    val youtrackProjectId: String,
    val telegramBotToken: String,
    val telegramBotUserName: String,
    val telegramChatId: String
)

fun loadConfig(): Config {
    val properties = Properties()
    val configFile = Config::class.java.getResourceAsStream("/config.properties")
    properties.load(configFile)

    return Config(
        youtrackUrl = properties.getProperty("youtrack.url"),
        youtrackToken = properties.getProperty("youtrack.token"),
        youtrackProjectId = properties.getProperty("youtrack.project.id"),
        telegramBotToken = properties.getProperty("telegram.bot.token"),
        telegramBotUserName = properties.getProperty("telegram.bot.username"),
        telegramChatId = properties.getProperty("telegram.chat.id")
    )
}