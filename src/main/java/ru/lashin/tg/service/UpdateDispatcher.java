package ru.lashin.tg.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.handlers.ExceptionHandler;
import ru.lashin.tg.service.handlers.Handler;
import ru.lashin.tg.service.resources.exceptions.UserException;
import java.util.Map;

/**
 * Класс отвечает за маршрутизацию и распределение входящего обновления по соответствующим обработчикам.
 */
@Slf4j
@Component
public class UpdateDispatcher {

    private final Map<String, Handler> handlers;
    private final Handler defaultHandler;
    private  final ExceptionHandler exceptionHandler;

    @Autowired
    public UpdateDispatcher(
            Map<String, Handler> handlers,
            @Qualifier("unknownUpdateQueryHandler") Handler defaultHandler, ExceptionHandler exceptionHandler) {
        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
        this.exceptionHandler = exceptionHandler;
    }

    public BotApiMethod<?> execute(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                update.getCallbackQuery().getMessage().getChatId();
                return handlers.get("callbackQueryHandler").answer(update);
            }
            update.getMessage().getChatId();
            if (update.hasMessage() && update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                if (text.startsWith("/")) {
                    return handlers.get("commandQueryHandler").answer(update);
                }
                return handlers.get("messageQueryHandler").answer(update);
            }
            log.info("Неподдерживаемая операция: {}", update);
            return defaultHandler.answer(update);
        } catch (UserException e) {
            return exceptionHandler.errorMessage(e, update);
        }
    }
}