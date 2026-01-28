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
 * Реализация логики кнопки удаления user из белого списка базы данных.
 */
@Slf4j
@Component
public class RemoveFromWhitelistButtonAction extends TwoStageButtonAction {


    @Autowired
    public RemoveFromWhitelistButtonAction(
            DatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(databaseManager, answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getCallbackQuery().getMessage().getChatId(),
                "Введите имя пользователя для удаления из белого списка:",
                null);
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        try {
            Integer userId = Integer.parseInt(update.getMessage().getText());
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
