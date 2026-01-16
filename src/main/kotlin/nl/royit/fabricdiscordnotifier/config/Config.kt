package nl.royit.fabricdiscordnotifier.config

// See: resources/player-discord-notifier.json

data class Config(
    val webhookUrl: String = EMPTY_STRING,
    val messages: MessagesConfig = MessagesConfig(),
) {
    data class MessagesConfig(
        val player: PlayerMessagesConfig = PlayerMessagesConfig(),
        val server: ServerMessagesConfig = ServerMessagesConfig(),
    )

    data class PlayerMessagesConfig(
        override val enabled: Boolean = true,
        override val webhookUrl: String = EMPTY_STRING,
        val enableJoinMessages: Boolean = true,
        val enableLeaveMessages: Boolean = true
    ) : GenericMessagesConfig

    data class ServerMessagesConfig(
        override val enabled: Boolean = true,
        override val webhookUrl: String = EMPTY_STRING,
        val enableStartedMessages: Boolean = true,
        val enableStoppedMessages: Boolean = true
    ) : GenericMessagesConfig

    interface GenericMessagesConfig {
        val enabled: Boolean
        val webhookUrl: String

        fun webhookUrl(): String? = webhookUrl.takeIf { it.isNotBlank() }
    }

    fun webhookUrl(): String? = webhookUrl.takeIf { it.isNotBlank() }

    companion object {
        const val EMPTY_STRING = ""
    }
}