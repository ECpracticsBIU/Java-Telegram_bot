package ru.lashin.tg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.lashin.tg.telegram.bot.Bot;

@Slf4j
@SpringBootApplication
public class TgApplication {


    public static void main(String[] args) {
        SpringApplication.run(TgApplication.class, args);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(Bot bot) throws TelegramApiException {
        log.info("Регистрация бота в Telegram API...");
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        log.info("Бот успешно зарегистрирован");
        return botsApi;
    }
}
