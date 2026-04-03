package ru.lashin.tg.service.menumodules.buttons;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

/**
 * Вспомогательная абстракция для тех кнопок, которые требуют дополнительных user-данных для выполнения базового
 * действия.
 */
public abstract class TwoStageButtonAction extends ButtonAction {

    public TwoStageButtonAction(
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
    }

    /**
     *
     * @param update входящее обновление.
     * @return сконфигурированный запрос дополнительных user-данных.
     */
    public abstract BotApiMethod<?> requestData(Update update);
}
