package ru.lashin.tg.service.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownUpdateHandler extends Handler {

    @Override
    public SendMessage answer(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Нет обработчика для таких данных")
                .build();
    }
}
