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
 * Реализация логики кнопки добавления сущности user в белый список базы данных.
 */
@Slf4j
@Component
public class AddToWhitelistButtonAction extends TwoStageButtonAction {

    private final WhiteListDatabaseManager databaseManager;

    @Autowired
    public AddToWhitelistButtonAction(
            @Qualifier("whiteListDatabaseManager") WhiteListDatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
        this.databaseManager = databaseManager;
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
        return answerMethodFactory.getAnswerCallBackQuery(
                update.getCallbackQuery().getId(),
                "Введите имя пользователя для белого списка:",
                true
        );
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
