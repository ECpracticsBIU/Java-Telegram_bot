package ru.lashin.tg.service.menumodules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.lashin.tg.service.keyboard.KeyboardFactory;

import java.util.List;

@Component
public class AdminMenuModule extends MenuModule {


    @Autowired
    public AdminMenuModule(KeyboardFactory keyboardFactory) {
        super(keyboardFactory);
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMenuInterface() {
        return keyboardFactory.createKeyboard(
                List.of(
                        "Get style",
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
