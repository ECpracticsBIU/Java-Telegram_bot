package ru.lashin.tg.service.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.StateDatabaseManager;
import ru.lashin.tg.service.menumodules.buttons.ButtonAction;
import ru.lashin.tg.service.menumodules.buttons.TwoStageButtonAction;
import ru.lashin.tg.service.menumodules.buttons.userbuttons.SetterStyleButtonAction;

import java.util.Map;

@Component
public class CallbackQueryHandler implements Handler {

    private final Map<String, ButtonAction> adminButtons;
    private final SetterStyleButtonAction userButton;
    private final StateDatabaseManager databaseManager;

    @Autowired
    public CallbackQueryHandler(
            Map<String, ButtonAction> adminButtons,
            @Qualifier("userButton") SetterStyleButtonAction userButton,
            @Qualifier("stateDatabaseManager") StateDatabaseManager databaseManager) {
        this.adminButtons = adminButtons;
        this.userButton = userButton;
        this.databaseManager = databaseManager;
    }

    @Transactional
    @Override
    public BotApiMethod<?> answer(Update update) {
        String data = update.getCallbackQuery().getData();
        ButtonAction button = adminButtons.getOrDefault(data, userButton);
        if (button instanceof TwoStageButtonAction b) {
            try {
                databaseManager.addChatIdToStateMemory(update.getCallbackQuery().getMessage().getChatId(), data);
                return b.requestData(update);
            } catch (DataAccessException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw e;
            }
        }
        return button.execute(update);
    }
}
