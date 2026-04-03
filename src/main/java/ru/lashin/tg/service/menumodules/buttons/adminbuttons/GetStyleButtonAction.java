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
import ru.lashin.tg.service.resources.exceptions.NotFoundDataException;

/**
 * Реализация логики кнопки получения сущности style из базы данных.
 */
@Slf4j
@Component
public class GetStyleButtonAction extends TwoStageButtonAction {

    private final StyleDatabaseManager databaseManager;

    @Autowired
    public GetStyleButtonAction(
            @Qualifier("styleDatabaseManager") StyleDatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
        this.databaseManager = databaseManager;
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getAnswerCallBackQuery(
                update.getCallbackQuery().getId(),
                "Введите название style:",
                true
        );
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
