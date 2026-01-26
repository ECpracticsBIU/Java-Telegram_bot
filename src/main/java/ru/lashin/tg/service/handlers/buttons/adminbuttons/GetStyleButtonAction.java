package ru.lashin.tg.service.handlers.buttons.adminbuttons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.DatabaseManager;
import ru.lashin.tg.service.handlers.buttons.TwoStageButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;
import ru.lashin.tg.service.resources.exceptions.NotFoundDataException;

/**
 * Реализация логики кнопки получения сущности style из базы данных.
 */
@Slf4j
@Component
public class GetStyleButtonAction extends TwoStageButtonAction {


    @Autowired
    public GetStyleButtonAction(
            DatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(databaseManager, answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getCallbackQuery().getMessage().getChatId(),
                "Введите название style:",
                null);
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String result = databaseManager.getStyle(update.getMessage().getText());
        if (result.isEmpty())
            throw new NotFoundDataException("Нет данных style с названием " + update.getMessage().getText());
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                result,
                null);
    }
}
