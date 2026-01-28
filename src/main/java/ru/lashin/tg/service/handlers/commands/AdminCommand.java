package ru.lashin.tg.service.handlers.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.adminsecurity.AccessChecker;
import ru.lashin.tg.service.resources.AnswerMethodFactory;
import ru.lashin.tg.service.menumodules.MenuModule;

import java.util.Map;

/**
 * Класс реализует логику обработки команды /admin.
 */
@Component("/admin")
public class AdminCommand extends Command {

    private final AccessChecker accessChecker;
    private final Map<String, MenuModule> menuModuleMap;

    @Autowired
    public AdminCommand(
            AnswerMethodFactory answerMethodFactory,
            AccessChecker accessChecker, Map<String, MenuModule> menuModuleMap1) {
        super(answerMethodFactory);
        this.accessChecker = accessChecker;
        this.menuModuleMap = menuModuleMap1;
    }


    /**
     *
     * @param update входящее обновление.
     * @return сконфигурированный ответ с элементами управления.
     */
    @Override
    public BotApiMethod<?> command(Update update) {
        if (accessChecker.checkUserAccess(update.getMessage().getFrom().getId())) {
            return answerMethodFactory.getSendMessage(
                    update.getMessage().getChatId(),
                    "Админ-панель",
                    menuModuleMap.get("adminMenuModule").getInlineKeyboardMenuInterface()
            );
        }
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "У вас нет прав доступа к админ-меню",
                null);
    }
}
