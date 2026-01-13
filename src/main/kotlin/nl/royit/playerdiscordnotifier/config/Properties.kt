package nl.royit.playerdiscordnotifier.config

data class Properties(
    val webhookUrl: String = WEBHOOK_URL_PLACEHOLDER,
    val enableJoinMessages: Boolean = true,
    val enableLeaveMessages: Boolean = true
) {
    companion object {
        const val WEBHOOK_URL_PLACEHOLDER = "PUT_DISCORD_WEBHOOK_URL_HERE"
    }
}
