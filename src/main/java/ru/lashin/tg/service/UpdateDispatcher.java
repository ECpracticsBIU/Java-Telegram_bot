package ru.lashin.tg.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.handlers.Handler;

import java.util.Map;

@Slf4j
@Component
public class UpdateDispatcher {

    /*
    Мапа, так как будут появляться новые обработчики (как минимум обработчик коллбек квери)
     */
    private final Map<String, Handler> handlers;
    private final Handler defaultHandler;

    @Autowired
    public UpdateDispatcher(
            Map<String, Handler> handlers,
            @Qualifier("unknownUpdateHandler") Handler defaultHandler) {
        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
    }

    public SendMessage execute(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (text.startsWith("/")) {
                return handlers.get("commandHandler").answer(update);
            }

            return defaultHandler.answer(update);
        }
        log.info("Неподдерживаемая операция: {}", update);
        return null;
    }
}
