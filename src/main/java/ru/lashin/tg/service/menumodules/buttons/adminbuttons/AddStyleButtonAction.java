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
 * Реализация логики кнопки добавления сущности style в базу данных.
 */
@Slf4j
@Component
public class AddStyleButtonAction extends TwoStageButtonAction {

    private final StyleDatabaseManager databaseManager;

    @Autowired
    public AddStyleButtonAction(
            @Qualifier("styleDatabaseManager") StyleDatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
        this.databaseManager = databaseManager;
    }

    @Override
    public BotApiMethod<?> requestData(Update update) {
       return answerMethodFactory.getAnswerCallBackQuery(
                update.getCallbackQuery().getId(),
                """
                Введите имя и json-данные для добавления style в следующем формате:

                style_name&{"key": "value"}

                Используйте все указанный символы.
                """,
               true
       );
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String[] text = update.getMessage().getText().split("&", 2);
        if (text.length < 2) throw new IncorrectInputDataException("Отсутствует разделитель &. " +
                "Название style и json-данные должны вводиться по форме. Повторите ввод:");
        databaseManager.addStyle(text[0].trim(), text[1].trim());
        log.info("Запись style успешно добавлена в базу данных");
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Style успешно добавлен",
                null
        );
    }
}
