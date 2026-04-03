package ru.lashin.tg.databasemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SecurityDatabaseManager extends DatabaseManager {

    @Autowired
    public SecurityDatabaseManager(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean containsAdminId(String adminId) {
        String sql = "select exists(select 1 from test.admin_ids where admin_id = ?);";
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, adminId));
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к списку администраторов \n{}", e.getMessage());
        }
        return false;
    }

    public boolean containsUserId(String userId) {
        String sql = "select exists(select 1 from test.users_whitelist where user_id = ?);";
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, userId));
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к списку пользователей \n{}", e.getMessage());
        }
        return false;
    }
}
