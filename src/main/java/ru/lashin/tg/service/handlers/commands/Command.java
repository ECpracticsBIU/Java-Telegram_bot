package ru.lashin.tg.service.handlers.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

/**
 * Абстрактный класс для всех команд. Реализация данного класса описывает логику обработки конкретной команды. Для
 * корректной работы программы класс-команду необходимо добавить в Spring-контекст и назвать идентично команде с
 * символом '/'.
 */
public abstract class Command {

    protected final AnswerMethodFactory answerMethodFactory;

    public Command(AnswerMethodFactory answerMethodFactory) {
        this.answerMethodFactory = answerMethodFactory;
    }

    public abstract BotApiMethod<?> command(Update update);
}
