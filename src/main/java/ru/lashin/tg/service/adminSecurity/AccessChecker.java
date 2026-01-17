package ru.lashin.tg.service.adminSecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lashin.tg.databaseManager.DatabaseManager;


@Component
public class AccessChecker {

    private final DatabaseManager databaseManager;

    @Autowired
    public AccessChecker(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean checkUserAccess(Long userId) {
        return databaseManager.containsUserId(userId);
    }
}
