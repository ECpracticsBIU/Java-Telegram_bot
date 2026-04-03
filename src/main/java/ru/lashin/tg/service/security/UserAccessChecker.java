package ru.lashin.tg.service.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.lashin.tg.databasemanager.SecurityDatabaseManager;

/**
 * Реализация запроса в базу данных на проверку доступа пользователя к ресурсам управления.
 */
@Component
public class UserAccessChecker implements AccessChecker {

    private final SecurityDatabaseManager databaseManager;

    @Autowired
    public UserAccessChecker(@Qualifier("securityDatabaseManager") SecurityDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    @Override
    public boolean checkAccess(String userId) {
        return databaseManager.containsUserId(userId);
    }
}
