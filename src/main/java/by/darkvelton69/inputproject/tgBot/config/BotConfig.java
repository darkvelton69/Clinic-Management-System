package by.darkvelton69.inputproject.tgBot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram.bot")
public record BotConfig(String name, String token) {
}