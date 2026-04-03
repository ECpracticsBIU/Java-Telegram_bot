package ru.lashin.tg.service.menumodules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.lashin.tg.databasemanager.StyleDatabaseManager;
import ru.lashin.tg.service.resources.exceptions.NotFoundDataException;
import ru.lashin.tg.service.resources.keyboard.KeyboardFactory;
import ru.lashin.tg.service.resources.AnswerMethodFactory;

import java.util.List;

@Component
public class UserMenuModule extends MenuModule {

    private final AnswerMethodFactory answerMethodFactory;
    private final StyleDatabaseManager styleDatabaseManager;

    @Autowired
    public UserMenuModule(
            KeyboardFactory keyboardFactory,
            AnswerMethodFactory answerMethodFactory,
            @Qualifier("styleDatabaseManager") StyleDatabaseManager styleDatabaseManager) {
        super(keyboardFactory);
        this.answerMethodFactory = answerMethodFactory;
        this.styleDatabaseManager = styleDatabaseManager;
    }

    @Override
    public BotApiMethod<?> provide(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                """
                        Выберите стиль, который будет обрабатывать ваш запрос:
                        """,
                keyboardMarkup()
        );
    }

    private InlineKeyboardMarkup keyboardMarkup() {
        List<String> styles = styleDatabaseManager.getAllStyles();
        if (styles.isEmpty()) throw new NotFoundDataException("Список со стилями пуст, обратитесь к администратору");
        return keyboardFactory.createVerticalKeyboard(styles, styles);
    }

    @Override
    public BotApiMethod<?> forbid(Update update) {
        return answerMethodFactory.getSendMessage(
                update.getMessage().getChatId(),
                "Вы еще не занесены в белый список",
                null
        );
    }
}
