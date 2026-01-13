package nl.royit.playerdiscordnotifier.helper

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logger {
    fun logger(): Logger = LoggerFactory.getLogger("player-discord-notifier")
}