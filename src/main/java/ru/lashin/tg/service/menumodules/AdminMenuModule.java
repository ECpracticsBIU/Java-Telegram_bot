package ru.lashin.tg.service.menumodules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.lashin.tg.service.resources.keyboard.KeyboardFactory;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

import java.util.List;

@Component
public class AdminMenuModule extends MenuModule {

    private final AnswerMethodFactory answerMethodFactory;

    @Autowired
    public AdminMenuModule(KeyboardFactory keyboardFactory, AnswerMethodFactory answerMethodFactory) {
        super(keyboardFactory);
        this.answerMethodFactory = answerMethodFactory;
    }

    @Override
    public BotApiMethod<?> provide(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Админ-панель",
                getInlineKeyboardMenuInterface()
        );
    }

    @Override
    public BotApiMethod<?> forbid(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "У вас нет прав доступа к админ-меню",
                null
        );
    }

    private InlineKeyboardMarkup getInlineKeyboardMenuInterface() {
        return keyboardFactory.createKeyboard(
                List.of("Get style",
                        "Add style",
                        "Remove style",
                        "Get whitelist",
                        "Add to whitelist",
                        "Remove from whitelist",
                        "Назад"),
                List.of(3, 3, 1),
                List.of("getStyleButtonAction",
                        "addStyleButtonAction",
                        "removeStyleButtonAction",
                        "getWhitelistButtonAction",
                        "addToWhitelistButtonAction",
                        "removeFromWhitelistButtonAction",
                        "adminBackButtonMenu")
        );
    }
}
