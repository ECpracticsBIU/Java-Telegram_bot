package ru.lashin.tg.service.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

@Component
public class UnknownUpdateQueryHandler implements Handler {

    private final AnswerMethodFactory answerMethodFactory;

    @Autowired
    public UnknownUpdateQueryHandler(AnswerMethodFactory answerMethodFactory) {
        this.answerMethodFactory = answerMethodFactory;
    }

    @Override
    public SendMessage answer(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Нет обработчика для таких данных",
                null
        );
    }
}
