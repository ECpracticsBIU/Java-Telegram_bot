package ru.lashin.tg.service.menuModules;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MenuModule {

    InlineKeyboardMarkup getButtonMenuInterface();
}
