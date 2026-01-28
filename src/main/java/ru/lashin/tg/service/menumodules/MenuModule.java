package ru.lashin.tg.service.menumodules;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.lashin.tg.service.keyboard.KeyboardFactory;

/**
 * Класс, наследующий данную абстракцию, будет предоставлять пользователю специализированное меню для взаимодействия с
 * функционалом бота.
 */
public abstract class MenuModule {

    protected final KeyboardFactory keyboardFactory;

    protected MenuModule(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    /**
     * Переопределение метода настраивает технические аспекты отображения меню взаимодействия с ботом.
     * @return UI взаимодействия с конкретным меню.
     */
    public abstract ReplyKeyboard getInlineKeyboardMenuInterface();
}
