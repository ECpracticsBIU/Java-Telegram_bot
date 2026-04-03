package ru.lashin.tg.service.handlers.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.security.AccessChecker;
import ru.lashin.tg.service.menumodules.MenuModule;

/**
 * Класс реализует логику обработки команды /admin.
 */
@Component("/admin")
public class AdminCommand extends Command {

    private final AccessChecker accessChecker;
    private final MenuModule adminMenuModule;

    @Autowired
    public AdminCommand(
            @Qualifier("adminAccessChecker") AccessChecker accessChecker,
            @Qualifier("adminMenuModule") MenuModule adminMenuModule) {
        this.accessChecker = accessChecker;
        this.adminMenuModule = adminMenuModule;
    }

    @Override
    public BotApiMethod<?> command(Update update) {
        if (accessChecker.checkAccess(update.getMessage().getFrom().getId().toString())) {
            return adminMenuModule.provide(update);
        }
        return adminMenuModule.forbid(update);
    }
}
