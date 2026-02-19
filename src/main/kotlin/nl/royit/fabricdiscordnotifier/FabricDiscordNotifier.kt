package nl.royit.fabricdiscordnotifier

import net.fabricmc.api.ModInitializer
import nl.royit.fabricdiscordnotifier.config.ConfigManager
import nl.royit.fabricdiscordnotifier.event.PlayerEventHandler
import nl.royit.fabricdiscordnotifier.event.ServerEventHandler
import nl.royit.fabricdiscordnotifier.helper.Logger.logger

@Suppress("kotlin:S6516") // Suppress: Functional interface implementations should use lambda expressions
object FabricDiscordNotifier : ModInitializer {

    override fun onInitialize() {
        ConfigManager.load()

        listOf(
            PlayerEventHandler,
            ServerEventHandler
        ).forEach { it.registerEventHandler() }

        logger.info("Fabric discord notifier initialized!")
    }
}