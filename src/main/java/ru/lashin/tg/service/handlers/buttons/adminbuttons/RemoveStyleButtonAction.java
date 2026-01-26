package ru.lashin.tg.service.handlers.buttons.adminbuttons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.DatabaseManager;
import ru.lashin.tg.service.handlers.buttons.TwoStageButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;
import ru.lashin.tg.service.resources.exceptions.IncorrectInputDataException;


/**
 * Реализация логики кнопки удаления сущности style из базы данных.
 */
@Slf4j
@Component
public class RemoveStyleButtonAction extends TwoStageButtonAction {


    @Autowired
    public RemoveStyleButtonAction(
            DatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(databaseManager, answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getCallbackQuery().getMessage().getChatId(),
                "Введите имя style для удаления:",
                null);
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String name = update.getMessage().getText();
        if (databaseManager.removeStyle(name) == 0)
            throw new IncorrectInputDataException(name + " в базе данных отсутствует");
        log.info("Запись style успешно удалена из базы данных");
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Style успешно удален",
                null);
    }
}
