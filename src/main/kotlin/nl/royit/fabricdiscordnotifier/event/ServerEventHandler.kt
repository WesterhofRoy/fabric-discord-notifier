package nl.royit.fabricdiscordnotifier.event

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import nl.royit.fabricdiscordnotifier.config.ConfigManager.config
import nl.royit.fabricdiscordnotifier.helper.Logger.logger
import nl.royit.fabricdiscordnotifier.messaging.DiscordMessageSender

object ServerEventHandler : EventHandler {
    override fun registerEventHandler() {
        val serverMessagesConfig = config.messages.server
        if (!serverMessagesConfig.enabled) return

        val webhookUrl = serverMessagesConfig.webhookUrl() ?: config.webhookUrl()
        if (webhookUrl.isNullOrBlank()) {
            logger.warn("Server messages enabled, but no webhook url found. Set the Discord Web URL in the config json")
            return
        }
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        if (serverMessagesConfig.enableStartedMessages) {
            ServerLifecycleEvents.SERVER_STARTED.register {
                val message = "[${LocalDateTime.now().format(dateTimeFormatter)}]  🟢  Server started!"

                DiscordMessageSender.sendMessage(webhookUrl, message)
            }
        }

        if (serverMessagesConfig.enableStoppedMessages) {
            ServerLifecycleEvents.SERVER_STOPPING.register {
                val message = "[${LocalDateTime.now().format(dateTimeFormatter)}]  🔴  Server stopped..."

                DiscordMessageSender.sendMessage(webhookUrl, message)
                DiscordMessageSender.shutdownGracefully()
            }
        }
    }
}