package ru.lashin.tg.service.handlers.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

@Component
public class UnknownCommand extends Command {

    public UnknownCommand(AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> command(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Неизвестная команда",
                null);
    }
}
