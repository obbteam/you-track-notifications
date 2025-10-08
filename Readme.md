# YouTrack Telegram Bot Project - Complete Code

This file contains the full source code for the YouTrack Telegram Bot, structured for a Maven project.

---

# YouTrack Telegram Bot 🤖

This project is a Kotlin-based bot that integrates your [YouTrack](https://www.jetbrains.com/youtrack/) instance with [Telegram](https://telegram.org/). It serves as a bridge, bringing important project updates directly into your Messenger and allowing you to interact with YouTrack without leaving your chat.

The application is built using **Hexagonal Architecture (Ports and Adapters)** to ensure a clean separation between the core application logic and external services.

<img width="490" height="321" alt="image" src="https://github.com/user-attachments/assets/2388fefe-d875-49cb-bfbd-eabf4c513a00" />

For those new to architecture, check out this [link](https://alistair.cockburn.us/hexagonal-architecture)

## Features

-   ✅ **Real-time Notifications**: Periodically fetches new notifications from your YouTrack account and sends them as formatted messages to a specified Telegram chat.
-   ✅ **Create Issues via Command**: Use a simple command in Telegram to create a new issue in a YouTrack project.
    -   Example: `/create Fix the login button bug`

---

## Demo video

Check out this link -> [Youtube](https://www.youtube.com/watch?v=gP3KI5ysVE4)

## Getting Started

Follow these instructions to get the bot running on your local machine.

### Prerequisites

-   **Java Development Kit (JDK)**: Version 11 or higher.
-   **Apache Maven**: To build the project and manage dependencies.
-   **A YouTrack Instance**: A running YouTrack account (Cloud or Standalone).
-   **A Telegram Account**.

### Configuration

All configuration is managed in a `config.properties` file. A template is provided.

**1. Create the Configuration File**

First, copy the template file `src/main/resources/config.properties.template` to create your local configuration file `src/main/resources/config.properties`.

**2. Fill in the Values**

Now, open `src/main/resources/config.properties` and fill in the following values.

| Key                     | Description                                                                     | How to Get It                                                                                                                                                                                                 |
| ----------------------- | ------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `youtrack.url`          | The base URL of your YouTrack instance.                                         | Example: `https://your-company.youtrack.cloud`                                                                                                                                                                |
| `youtrack.token`        | A permanent YouTrack API token with sufficient permissions.                     | In YouTrack, go to your **Profile** > **Authentication** > **New token**. Grant it the "YouTrack" scope.                                                                                                      |
| `youtrack.project.id`   | The **internal ID** of the YouTrack project where new issues will be created.   | Go to **Projects**, click your project, then click **Project Settings**. The ID is in the URL: `.../projects/**0-1**?tab=...`. **Note:** This is often a number pair (like `0-1`), not the short name (like `TIA`). |
| `telegram.bot.username` | The username of your Telegram bot.                                              | Create a new bot by talking to `@BotFather` on Telegram and using the `/newbot` command.                                                                                                                      |
| `telegram.bot.token`    | The secret API token for your Telegram bot.                                     | **BotFather** will give you this token when you create your bot. Keep it safe.                                                                                                                                |
| `telegram.chat.id`      | The ID of the Telegram chat where the bot will send notifications.              | Send a message to the bot `@userinfobot` on Telegram. It will immediately reply with your unique Chat ID.                                                                                                     |

---

### Installation and Running

1.  **Build the project with Maven:**
    ```bash
    mvn clean install
    ```

2.  **Run the application:**
    ```bash
    mvn exec:java
    ```

You should see "Bot started successfully!" in your console.
