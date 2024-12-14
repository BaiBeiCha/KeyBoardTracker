package org.baibei.keyboardtrackertgbot.configs;

import org.baibei.keyboardtrackertgbot.pojo.other.BotToken;
import org.baibei.keyboardtrackertgbot.pojo.passwordencoder.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Bean
    public BotToken getBotToken() {
        return new BotToken("7836952551:AAHFkL6fZ1jlmJPYW1aVcjho15U53t92zmo");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder();
    }
}
