package ru.lashin.tg.service.handlers.buttons;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.DatabaseManager;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

/**
 * Вспомогательная абстракция для тех кнопок, которые требуют дополнительных user-данных для выполнения базового
 * действия.
 */
public abstract class TwoStageButtonAction extends ButtonAction {

    public TwoStageButtonAction(
            DatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(databaseManager, answerMethodFactory);
    }

    /**
     *
     * @param update входящее обновление.
     * @return сконфигурированный запрос дополнительных user-данных.
     */
    public abstract BotApiMethod<?> requestData(Update update);
}
