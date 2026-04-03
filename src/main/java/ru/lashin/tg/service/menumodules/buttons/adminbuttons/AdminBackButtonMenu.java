package ru.lashin.tg.service.menumodules.buttons.adminbuttons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.menumodules.buttons.ButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;


/**
 * Реализация логики кнопки для удаления админ-панели.
 */
@Component
public class AdminBackButtonMenu extends ButtonAction {


    @Autowired
    public AdminBackButtonMenu(
            AnswerMethodFactory answerMethodFactory) {
        super(answerMethodFactory);
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        return answerMethodFactory.getDeleteMessage(
                update.getCallbackQuery().getMessage().getChatId(),
                update.getCallbackQuery().getMessage().getMessageId());
    }
}
