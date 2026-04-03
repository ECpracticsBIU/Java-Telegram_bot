package ru.lashin.tg.service.menumodules;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.resources.keyboard.KeyboardFactory;

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
    public abstract BotApiMethod<?> provide(Update update);

    public abstract BotApiMethod<?> forbid(Update update);
}
