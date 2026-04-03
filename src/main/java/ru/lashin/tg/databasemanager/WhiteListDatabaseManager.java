package ru.lashin.tg.databasemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class WhiteListDatabaseManager extends DatabaseManager {

    public WhiteListDatabaseManager(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<String> getWhitelist() {
        String sql = "select username from test.users_whitelist";
        try {
            return jdbcTemplate.queryForList(sql, String.class);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на получение данных whitelist: \n{}", e.getMessage());
            throw e;
        }
    }

    public int addToWhitelist(String userId) {
        String sql = "insert into test.users_whitelist (user_id, is_active) values (?, ?)";
        try {
            return jdbcTemplate.update(sql, userId, true);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на добавление данных whitelist: \n{}", e.getMessage());
            throw e;
        }
    }

    public int removeFromWhitelist(String userId) {
        String sql = "delete from test.users_whitelist where user_id = ?";
        try {
            return jdbcTemplate.update(sql, userId);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на удаление данных whitelist: \n{}", e.getMessage());
            throw e;
        }
    }

    public void setStyleForUser(String styleName, String userId) {
        String sql = "update test.users_whitelist set style_id = (select style_id from test.styles_config where name = ?) where user_id = ?";
        try {
            jdbcTemplate.update(sql, styleName, userId);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на установку стиля для пользователя из белого списка");
            throw e;
        }
    }

    public List<String> getStyleByUser(String userId) {
        String sql = "select s.config_json from test.users_whitelist w join test.styles_config s on w.style_id = s.style_id where w.user_id = ?";
        try {
            return jdbcTemplate.queryForList(sql, String.class, userId);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на получение json-данных пользователя");
            throw e;
        }
    }
}
