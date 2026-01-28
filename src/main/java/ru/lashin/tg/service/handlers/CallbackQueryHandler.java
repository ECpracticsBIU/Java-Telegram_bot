package ru.lashin.tg.service.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.DatabaseManager;
import ru.lashin.tg.service.handlers.buttons.ButtonAction;
import ru.lashin.tg.service.handlers.buttons.TwoStageButtonAction;

import java.util.Map;

/**
 * Обработчик входящего обновления в виде callback data. Так как callback data напрямую связана с кнопками клавиатуры,
 * данный класс распределяет входящую callback data по соответствующим классам-кнопкам. Кнопки подгружаются в карту
 * buttons автоматически с помощью Spring DI.
 */
@Component
public class CallbackQueryHandler implements Handler {

    private final Map<String, ButtonAction> buttons;
    private final DatabaseManager databaseManager;

    @Autowired
    public CallbackQueryHandler(Map<String, ButtonAction> buttons, DatabaseManager databaseManager) {
        this.buttons = buttons;
        this.databaseManager = databaseManager;
    }

    /**
     * Метод определяет тип кнопки (кнопка требует ввод дополнительных данных или нет) и вызывает соответствующий метод.
     * В случае получения callback data с двухэтапной кнопки предварительно в базу данных фиксируется информация, что
     * чат будет ожидать дополнительных данных в следующем запросе. Фиксация происходит только в том случае, если на
     * этапе выполнения метода кнопки не было ошибки, иначе транзакция не фиксирует запись.
     * При обработке кнопки, не требующей дополнительных данных, вызывается основной ее метод.
     * @param update входящее обновление.
     * @return сконфигурированный ответ.
     */
    @Transactional
    @Override
    public BotApiMethod<?> answer(Update update) {
        String data = update.getCallbackQuery().getData();
        ButtonAction button = buttons.get(data);
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
