package ru.lashin.tg.service.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Фабрика создания UI-клавиатуры.
 */

@Component
public class KeyboardFactory {

    /**
     *
     * @param text текст кнопок;
     * @param configuration конфигурация расположения кнопок;
     * @param data callback data кнопок.
     * @return сконфигурированная InlineKeyboardMarkup.
     */
    public InlineKeyboardMarkup createKeyboard(
            List<String> text,
            List<Integer> configuration,
            List<String> data) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        int index = 0;
        for (Integer rowNumber : configuration) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < rowNumber; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(text.get(index));
                button.setCallbackData(data.get(index));
                row.add(button);
                index++;
            }
            keyboard.add(row);
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }
}
