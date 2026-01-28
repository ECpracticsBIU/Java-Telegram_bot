package ru.lashin.tg.service.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.lashin.tg.service.handlers.commands.Command;

import java.util.Map;

/**
 * Обработчик входящего обновления в виде command. Конкретные реализации обработки команд внедряются в карту commands.
 */
@Component
public class CommandQueryHandler implements Handler {

    private final Map<String, Command> commands;

    @Autowired
    public CommandQueryHandler(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public BotApiMethod<?> answer(Update update) {
        return commands.getOrDefault(
                update.getMessage().getText(),
                commands.get("unknownCommand")).command(update);
    }
}
