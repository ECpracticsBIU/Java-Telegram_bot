package ru.lashin.tg.service.menumodules.buttons.userbuttons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.WhiteListDatabaseManager;
import ru.lashin.tg.service.menumodules.buttons.ButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

@Component("userButton")
public class SetterStyleButtonAction extends ButtonAction {

    private final WhiteListDatabaseManager whiteListDatabaseManager;

    @Autowired
    public SetterStyleButtonAction(
            AnswerMethodFactory answerMethodFactory,
            WhiteListDatabaseManager whiteListDatabaseManager) {
        super(answerMethodFactory);
        this.whiteListDatabaseManager = whiteListDatabaseManager;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String styleName = update.getCallbackQuery().getData();
        whiteListDatabaseManager.setStyleForUser(styleName, update.getCallbackQuery().getFrom().getId().toString());
        return answerMethodFactory.getAnswerCallBackQuery(
                update.getCallbackQuery().getId(),
                "Для вас установлен стиль запросов " + styleName,
                true
        );
    }
}
