package ru.lashin.tg.service.menumodules.buttons.adminbuttons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.StyleDatabaseManager;
import ru.lashin.tg.service.menumodules.buttons.TwoStageButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;
import ru.lashin.tg.service.resources.exceptions.IncorrectInputDataException;


/**
 * Реализация логики кнопки удаления сущности style из базы данных.
 */
@Slf4j
@Component
public class RemoveStyleButtonAction extends TwoStageButtonAction {

    private final StyleDatabaseManager databaseManager;

    @Autowired
    public RemoveStyleButtonAction(
            @Qualifier("styleDatabaseManager") StyleDatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
        this.databaseManager = databaseManager;
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getAnswerCallBackQuery(
                update.getCallbackQuery().getId(),
                "Введите имя style для удаления:",
                true
        );
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
