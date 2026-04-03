package ru.lashin.tg.service.menumodules.buttons;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

/**
 * Базовая абстракция основного действия кнопки.
 */
public abstract class ButtonAction {

    protected final AnswerMethodFactory answerMethodFactory;


    public ButtonAction(
            AnswerMethodFactory answerMethodFactory) {
        this.answerMethodFactory = answerMethodFactory;
    }

    /**
     *
     * @param update входящее обновление.
     * @return сконфигурированный результат нажатия кнопки.
     */
    public abstract BotApiMethod<?> execute(Update update);

}
