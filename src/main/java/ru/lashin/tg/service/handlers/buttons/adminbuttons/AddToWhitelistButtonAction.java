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
 * Реализация логики кнопки добавления сущности user в белый список базы данных.
 */
@Slf4j
@Component
public class AddToWhitelistButtonAction extends TwoStageButtonAction {


    @Autowired
    public AddToWhitelistButtonAction(
            DatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(databaseManager, answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getCallbackQuery().getMessage().getChatId(),
                "Введите имя пользователя для белого списка:",
                null);
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        int count = databaseManager.addToWhitelist(update.getMessage().getText());
        if (count == 0) throw new IncorrectInputDataException("Вы ввели некорректные данные. Попробуйте снова:");
        log.info("Запись whitelist успешно добавлена в базу данных");
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Пользователь успешно добавлен в белый список",
                null);
    }
}
