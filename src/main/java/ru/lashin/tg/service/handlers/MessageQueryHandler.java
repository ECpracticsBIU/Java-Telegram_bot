package ru.lashin.tg.service.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.DatabaseManager;
import ru.lashin.tg.service.handlers.buttons.ButtonAction;
import ru.lashin.tg.service.resources.exceptions.IncorrectInputDataException;

import java.util.Map;

/**
 * Обработчик входящего обновления в виде текстового сообщения.
 */
@Slf4j
@Component
public class MessageQueryHandler implements Handler {

    private final Map<String, ButtonAction> buttons;
    private final DatabaseManager databaseManager;

    @Autowired
    public MessageQueryHandler(
            Map<String, ButtonAction> buttons,
            DatabaseManager databaseManager) {
        this.buttons = buttons;
        this.databaseManager = databaseManager;
    }

    /**
     * Метод выполняет проверку на то, ожидает ли чат обновление дополнительных данных. Если ожидает, то транзакционно
     * метод удаляет состояние для чата и выполняет прямое действие кнопки, используя полученные дополнительные данные.
     * В противном случае метод возвращает сообщение о том, что входящие данные неизвестны.
     */
    @Transactional
    @Override
    public BotApiMethod<?> answer(Update update) {
        String chatState = databaseManager.getChatState(update.getMessage().getChatId());
        if (!chatState.isEmpty()) {
            try {
                databaseManager.removeChatIdFromStateMemory(update.getMessage().getChatId());
                return buttons.get(chatState).execute(update);
            } catch (DataAccessException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw e;
            }
        }
        throw new IncorrectInputDataException("Неизвестные данные");
    }
}
