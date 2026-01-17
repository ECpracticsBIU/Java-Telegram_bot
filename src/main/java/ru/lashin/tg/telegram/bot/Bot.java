package ru.lashin.tg.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.lashin.tg.service.UpdateDispatcher;
import ru.lashin.tg.telegram.properties.ApplicationProperties;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    private final ApplicationProperties properties;
    private final UpdateDispatcher updateDispatcher;

    @Autowired
    public Bot(ApplicationProperties properties, UpdateDispatcher updateDispatcher) {
        this.properties = properties;
        this.updateDispatcher = updateDispatcher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(updateDispatcher.execute(update));
        } catch (TelegramApiException e) {
            log.error(e.toString());
        }
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }
}
