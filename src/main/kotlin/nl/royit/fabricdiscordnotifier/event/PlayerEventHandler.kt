package nl.royit.fabricdiscordnotifier.event

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import nl.royit.fabricdiscordnotifier.config.ConfigManager.config
import nl.royit.fabricdiscordnotifier.helper.Logger.logger
import nl.royit.fabricdiscordnotifier.messaging.DiscordMessageSender

object PlayerEventHandler : EventHandler {
    override fun registerEventHandler() {
        val playerMessagesConfig = config.messages.player
        if (!playerMessagesConfig.enabled) return

        val webhookUrl = playerMessagesConfig.webhookUrl() ?: config.webhookUrl()
        if (webhookUrl.isNullOrBlank()) {
            logger.warn("Player messages enabled, but no webhook url found. Set the Discord Web URL in the config json")
            return
        }

        if (playerMessagesConfig.enableJoinMessages) {
            ServerPlayConnectionEvents.JOIN.register { handler, _, server ->
                val player = handler.player
                val playerNames = server.playerNames.plus(player.name.string).joinToString(", ")

                val message = "🟢  ${player.name.string} joined! (${server.playerCount + 1} online)" +
                    "\nPlayers online: $playerNames"

                DiscordMessageSender.sendMessage(webhookUrl, message)
            }
        }

        if (playerMessagesConfig.enableLeaveMessages) {
            ServerPlayConnectionEvents.DISCONNECT.register { handler, server ->
                val player = handler.player
                val playerCount = server.playerCount - 1

                val message = "🔴  ${player.name.string} left.. ($playerCount online)"

                DiscordMessageSender.sendMessage(webhookUrl, message)
            }
        }
    }
}