package ru.lashin.tg.service.handlers.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.menumodules.UserMenuModule;
import ru.lashin.tg.service.security.UserAccessChecker;


@Component("/start")
public class StartCommand extends Command {

    private final UserAccessChecker userAccessChecker;
    private final UserMenuModule userMenuModule;

    @Autowired
    public StartCommand(
            UserAccessChecker userAccessChecker,
            UserMenuModule userMenuModule) {
        this.userAccessChecker = userAccessChecker;
        this.userMenuModule = userMenuModule;
    }

    @Override
    public BotApiMethod<?> command(Update update) {
        if (userAccessChecker.checkAccess(update.getMessage().getFrom().getId().toString())) {
            return userMenuModule.provide(update);
        }
        return userMenuModule.forbid(update);
    }
}
