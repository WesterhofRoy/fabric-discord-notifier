package nl.royit.playerdiscordnotifier.messaging

import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import nl.royit.playerdiscordnotifier.helper.Logger.logger

object DiscordMessageSender {
    private val executor = Executors.newSingleThreadExecutor {
        Thread(it, "discord-webhook-notifier").apply { isDaemon = true }
    }
    private val httpClient = HttpClient.newBuilder()
        .executor(executor)
        .build()
    private val gson = Gson()

    fun sendMessage(webhook: String, content: String) {
        val message = DiscordMessage(content)
        val messageString = gson.toJson(message)

        val request = HttpRequest.newBuilder()
            .uri(URI.create(webhook))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(messageString))
            .build()

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
            .exceptionally { ex -> null.also { logger.warn("Failed to send discord message", ex) } }
    }

    fun shutdownGracefully() {
        executor.shutdown()
        executor.awaitTermination(2, TimeUnit.SECONDS)
    }
}