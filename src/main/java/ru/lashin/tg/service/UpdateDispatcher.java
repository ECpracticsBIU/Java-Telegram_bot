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

/**
 * Класс отвечает за маршрутизацию и распределение входящего обновления по соответствующим обработчикам.
 */
@Slf4j
@Component
public class UpdateDispatcher {

    private final Handler callbackQueryHandler;
    private final Handler commandQueryHandler;
    private final Handler messageQueryHandler;
    private final Handler defaultHandler;
    private final ExceptionHandler exceptionHandler;



    @Autowired
    public UpdateDispatcher(
            @Qualifier("callbackQueryHandler") Handler callbackQueryHandler,
            @Qualifier("commandQueryHandler") Handler commandQueryHandler,
            @Qualifier("messageQueryHandler") Handler messageQueryHandler,
            @Qualifier("unknownUpdateQueryHandler") Handler defaultHandler,
            ExceptionHandler exceptionHandler) {
        this.callbackQueryHandler = callbackQueryHandler;
        this.commandQueryHandler = commandQueryHandler;
        this.messageQueryHandler = messageQueryHandler;
        this.defaultHandler = defaultHandler;
        this.exceptionHandler = exceptionHandler;
    }

    public BotApiMethod<?> execute(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                update.getCallbackQuery().getMessage().getChatId();
                return callbackQueryHandler.answer(update);
            }
            if (update.hasMessage() && update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                if (text.startsWith("/")) {
                    return commandQueryHandler.answer(update);
                }
                return messageQueryHandler.answer(update);
            }
            log.info("Неподдерживаемая операция: {}", update);
            return defaultHandler.answer(update);
        } catch (UserException e) {
            return exceptionHandler.errorMessage(e, update);
        }
    }
}