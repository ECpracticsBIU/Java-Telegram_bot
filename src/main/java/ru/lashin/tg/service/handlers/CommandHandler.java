package ru.lashin.tg.service.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.menuModules.MenuModule;
import ru.lashin.tg.service.adminSecurity.AccessChecker;

@Component
public class CommandHandler extends Handler {

    private final AccessChecker accessChecker;

    @Autowired
    public CommandHandler(MenuModule menuModule, AccessChecker accessChecker) {
        super(menuModule);
        this.accessChecker = accessChecker;
    }

    @Override
    public SendMessage answer(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        if (update.getMessage().getText().equals("/admin")) {
            if (accessChecker.checkUserAccess(update.getMessage().getFrom().getId())) {
                sendMessage.setText("Админ-панель:");
                sendMessage.setReplyMarkup(menuModule.getButtonMenuInterface());
            } else {
                sendMessage.setText("Нет права доступа к админ-меню");
            }
        } else {
            sendMessage.setText("Неизвестная команда");
        }
        return sendMessage;
    }
}
