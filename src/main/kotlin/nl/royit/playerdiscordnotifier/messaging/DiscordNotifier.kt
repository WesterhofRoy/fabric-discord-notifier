package nl.royit.playerdiscordnotifier.messaging

import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.Executors
import nl.royit.playerdiscordnotifier.config.PropertiesManager.config
import nl.royit.playerdiscordnotifier.helper.Logger.logger

object DiscordNotifier {
    private val logger = logger()
    private val executor = Executors.newSingleThreadExecutor {
        Thread(it, "discord-webhook-notifier").apply { isDaemon = true }
    }
    private val httpClient = HttpClient.newBuilder()
        .executor(executor)
        .build()

    fun sendMessage(content: String) {
        val message = DiscordMessage(content)
        val messageString = Gson().toJson(message)

        val request = HttpRequest.newBuilder()
            .uri(URI.create(config.webhookUrl))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(messageString))
            .build()

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
            .exceptionally {
                logger.warn("Failed to send discord message", it)
                null
            }
    }
}