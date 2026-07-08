# fabric-discord-notifier

A simple Fabric mod that sends Minecraft server notifications to a Discord channel via a webhook.

## Features

- Sends Discord messages when players join or leave the server
- Notifies when the server starts or stops
- Configurable message toggles and webhook URLs

## Installation

1. Download the latest release from the [releases page](https://github.com/WesterhofRoy/fabric-discord-notifier/releases).
2. Place the mod `.jar` file in your server's `mods` folder.
3. Start the server once to generate the config file (`config/player-discord-notifier.json`).
4. Edit the config file to add your Discord webhook URL and adjust settings as needed.

## Configuration

- The config file is located at `config/player-discord-notifier.json`.
- Set your Discord webhook URL in the `webhookUrl` field.
- Enable or disable specific notifications as desired.

## Requirements

- Minecraft >=1.20.1 <26.3 (tested up to 26.2)
- Fabric Loader 0.18.4 or newer
- Java 21 or newer

## Contributing

Contributions are welcome!  
If you have suggestions, bug reports, or want to submit a pull request, please open an issue or fork the repository.
Make sure your code follows the existing style and includes appropriate documentation and tests where relevant.

## License

This project is licensed under the Apache 2.0 License.

---

[Source code](https://github.com/WesterhofRoy/fabric-discord-notifier)
