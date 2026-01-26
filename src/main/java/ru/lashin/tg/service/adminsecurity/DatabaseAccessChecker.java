package ru.lashin.tg.service.adminsecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lashin.tg.databasemanager.DatabaseManager;

/**
 * Реализация запроса в базу данных на проверку доступа пользователя к ресурсам управления.
 */
@Component
public class DatabaseAccessChecker implements AccessChecker {

    private final DatabaseManager databaseManager;

    @Autowired
    public DatabaseAccessChecker(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    @Override
    public boolean checkUserAccess(Long userId) {
        return databaseManager.containsUserId(userId);
    }
}
