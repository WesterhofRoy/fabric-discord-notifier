package nl.royit.playerdiscordnotifier

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import nl.royit.playerdiscordnotifier.config.Properties.Companion.WEBHOOK_URL_PLACEHOLDER
import nl.royit.playerdiscordnotifier.config.PropertiesManager
import nl.royit.playerdiscordnotifier.config.PropertiesManager.config
import nl.royit.playerdiscordnotifier.helper.Logger.logger
import nl.royit.playerdiscordnotifier.messaging.DiscordNotifier

object PlayerDiscordNotifier : ModInitializer {
    private val logger = logger()

    override fun onInitialize() {
        PropertiesManager.load()

        ServerPlayConnectionEvents.JOIN.register { handler, _, server ->
            if (!config.enableJoinMessages) return@register

            val player = handler.player
            val playerCount = server.currentPlayerCount + 1
            val message = "🟢 ${player.name.string} joined! ($playerCount online)"

            logger.info(message)
            if (config.webhookUrl != WEBHOOK_URL_PLACEHOLDER) {
                DiscordNotifier.sendMessage(message)
            }
        }

        ServerPlayConnectionEvents.DISCONNECT.register { handler, server ->
            if (!config.enableLeaveMessages) return@register

            val player = handler.player
            val playerCount = server.currentPlayerCount - 1
            val message = "🔴 ${player.name.string} left! ($playerCount online)"

            logger.info(message)
            if (config.webhookUrl != WEBHOOK_URL_PLACEHOLDER) {
                DiscordNotifier.sendMessage(message)
            }
        }
    }
}