package ru.lashin.tg.service.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.menuModules.MenuModule;


public abstract class Handler {

    protected MenuModule menuModule;

    public Handler(){}

    public Handler(MenuModule menuModule) {
        this.menuModule = menuModule;
    }

    public abstract SendMessage answer(Update update);
}
