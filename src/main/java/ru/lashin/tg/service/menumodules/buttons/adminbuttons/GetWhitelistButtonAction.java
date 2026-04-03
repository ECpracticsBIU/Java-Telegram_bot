package ru.lashin.tg.service.menumodules.buttons.adminbuttons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.WhiteListDatabaseManager;
import ru.lashin.tg.service.menumodules.buttons.ButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;
import ru.lashin.tg.service.resources.exceptions.NotFoundDataException;

import java.util.List;


/**
 * Реализация логики кнопки получения всего белого списка базы данных.
 */
@Slf4j
@Component
public class GetWhitelistButtonAction extends ButtonAction {

    private final WhiteListDatabaseManager databaseManager;

    @Autowired
    public GetWhitelistButtonAction(
            @Qualifier("whiteListDatabaseManager") WhiteListDatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
        this.databaseManager = databaseManager;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        List<String> result = databaseManager.getWhitelist();
        if (result.isEmpty())
            throw new NotFoundDataException("Белый список пуст");
        log.info("Запрос на получение данных whitelist успешно выполнен");
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                result.toString(),
                null
        );
    }
}
