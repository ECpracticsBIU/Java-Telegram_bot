package ru.lashin.tg.service.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.databasemanager.StateDatabaseManager;
import ru.lashin.tg.databasemanager.WhiteListDatabaseManager;
import ru.lashin.tg.service.menumodules.buttons.ButtonAction;
import ru.lashin.tg.service.resources.AnswerMethodFactory;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MessageQueryHandler implements Handler {

    private final Map<String, ButtonAction> buttons;
    private final StateDatabaseManager databaseManager;
    private final AnswerMethodFactory answerMethodFactory;
    private final WhiteListDatabaseManager whiteListDatabaseManager;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageQueryHandler(
            Map<String, ButtonAction> buttons,
            @Qualifier("stateDatabaseManager") StateDatabaseManager databaseManager,
            AnswerMethodFactory answerMethodFactory,
            @Qualifier("whiteListDatabaseManager")
            WhiteListDatabaseManager whiteListDatabaseManager, ObjectMapper objectMapper) {
        this.buttons = buttons;
        this.databaseManager = databaseManager;
        this.answerMethodFactory = answerMethodFactory;
        this.whiteListDatabaseManager = whiteListDatabaseManager;
        this.objectMapper = objectMapper;
    }

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

        List<String> result = whiteListDatabaseManager.getStyleByUser(update.getMessage().getFrom().getId().toString());
        if (result.isEmpty()) {
            return answerMethodFactory.getSendMessage(
                    update.getMessage().getChatId(),
                    "Вас не внесли в белый список или вы не выбрали стиль запроса",
                    null
            );
        }
        Map<?,?> jsonData = objectMapper.readValue(result.getFirst(), Map.class);
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Принял в работу...\n" + jsonData.toString(),
                null
        ); //TODO
    }
}
