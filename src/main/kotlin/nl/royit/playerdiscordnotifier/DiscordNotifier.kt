package nl.royit.playerdiscordnotifier

import net.fabricmc.api.ModInitializer
import nl.royit.playerdiscordnotifier.config.ConfigManager
import nl.royit.playerdiscordnotifier.event.PlayerEventHandler
import nl.royit.playerdiscordnotifier.event.ServerEventHandler

@Suppress("kotlin:S6516") // Suppress: Functional interface implementations should use lambda expressions
object DiscordNotifier : ModInitializer {

    override fun onInitialize() {
        ConfigManager.load()

        listOf(
            PlayerEventHandler,
            ServerEventHandler
        ).forEach { it.registerEventHandler() }
    }
}