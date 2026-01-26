package ru.lashin.tg.service.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.resources.AnswerMethodFactory;


/**
 * Обработчик ошибок, вызванных некорректным вводом данных пользователем или возникших в результате запроса к базе
 * данных.
 */
@Component
public class ExceptionHandler {

    private final AnswerMethodFactory answerMethodFactory;

    @Autowired
    public ExceptionHandler(AnswerMethodFactory answerMethodFactory) {
        this.answerMethodFactory = answerMethodFactory;
    }

    public SendMessage errorMessage(Exception e, Update update) {
        Long chatId = update.hasCallbackQuery()
                ? update.getCallbackQuery().getMessage().getChatId()
                : update.getMessage().getChatId();
        return answerMethodFactory.getSendMessage(
                chatId,
                e.getMessage(),
                null
        );
    }

}
