package ru.lashin.tg.service.menumodules.buttons.adminbuttons;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.WhiteListDatabaseManager;
import ru.lashin.tg.service.menumodules.buttons.TwoStageButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;
import ru.lashin.tg.service.resources.exceptions.IncorrectInputDataException;


/**
 * Реализация логики кнопки удаления user из белого списка базы данных.
 */
@Slf4j
@Component
public class RemoveFromWhitelistButtonAction extends TwoStageButtonAction {

    private final WhiteListDatabaseManager databaseManager;

    @Autowired
    public RemoveFromWhitelistButtonAction(
            @Qualifier("whiteListDatabaseManager") WhiteListDatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
        this.databaseManager = databaseManager;
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getAnswerCallBackQuery(
                update.getCallbackQuery().getId(),
                "Введите имя пользователя для удаления из белого списка:",
                true
        );
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        try {
            String userId = update.getMessage().getText();
            int count = databaseManager.removeFromWhitelist(userId);
            if (count == 0) throw new IncorrectInputDataException(userId + " отсутствует в БД");
        } catch (NumberFormatException e) {
            throw new IncorrectInputDataException("Введите user id в формате числа:");
        }
        log.info("Запись whitelist успешно удалена из базы данных");
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Пользователь успешно удален из белого списка",
                null);
    }
}
