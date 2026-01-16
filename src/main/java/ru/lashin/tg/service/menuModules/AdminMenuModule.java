package ru.lashin.tg.service.menuModules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.lashin.tg.service.keyboard.KeyboardFactory;

import java.util.List;

@Component
public class AdminMenuModule implements MenuModule {

    private final KeyboardFactory keyboardFactory;

    @Autowired
    public AdminMenuModule(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public InlineKeyboardMarkup getButtonMenuInterface() {
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
                // TODO здесь потом будет callback data
                List.of("получить_промт",
                        "добавить_промт",
                        "удалить_промт",
                        "получить_белый список",
                        "добавить_в_белый список",
                        "удалить_из_белого списка",
                        "назад"));
    }
}
