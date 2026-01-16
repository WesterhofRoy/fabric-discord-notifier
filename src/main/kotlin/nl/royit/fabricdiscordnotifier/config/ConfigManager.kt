package nl.royit.fabricdiscordnotifier.config

import com.google.gson.GsonBuilder
import java.nio.file.Files
import net.fabricmc.loader.api.FabricLoader
import nl.royit.fabricdiscordnotifier.helper.Logger.logger

object ConfigManager {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val configPath = FabricLoader.getInstance().configDir.resolve(CONFIG_PATH)

    lateinit var config: Config

    fun load() {
        if (Files.notExists(configPath)) {
            logger.warn("Config file not found, creating default at: ${configPath.toAbsolutePath()}.")
            logger.warn("If you don't specify the discord webhook url in the config, notifications will only be send in the in-game chat.")
            logger.warn("This message will only be displayed when loading the server for the first time.")
            config = Config()
            save()
            return
        }

        Files.newBufferedReader(configPath).use {
            config = gson.fromJson(it, Config::class.java)
        }
        logger.info("Config loaded.")
    }

    private fun save() {
        Files.newBufferedWriter(configPath).use {
            gson.toJson(config, it)
        }
    }

    private const val CONFIG_PATH = "player-discord-notifier.json"
}