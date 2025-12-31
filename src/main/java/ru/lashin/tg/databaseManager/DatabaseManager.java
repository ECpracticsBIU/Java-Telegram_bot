package ru.lashin.tg.databaseManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class DatabaseManager {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getStyle(String name) {
        String sql = "select config_json from test.styles_config where name = ?";
        try {
            String result = jdbcTemplate.queryForObject(sql, String.class, name);
            log.info("Запрос на получение данных успешно выполнен");
            return result;

        } catch (DataAccessException e) {
            log.error("Ошибка запроса получения данных\n" + e.getCause().getMessage());
            return null;
        }
    }

    public void addStyle(String name, String data) {
        String sql = "insert into test.styles_config (name, config_json) values (?, ?::jsonb)";
        try {
            jdbcTemplate.update(sql, name, data);
            log.info("Запись успешно добавлена в базу данных");

        } catch (DataAccessException e) {
            log.error("Ошибка добавления записи в базу данных\n" + e.getCause().getMessage());
        }
    }

    public void deleteStyle(String name) {
        String sql = "delete from test.styles_config where name = ?";
        try {
            jdbcTemplate.update(sql, name);
            log.info("Запись успешно удалена из базы данных");

        } catch (DataAccessException e) {
            log.error("Ошибка удаления записи \n" + e.getCause().getMessage());
        }
    }

    public List<String> getWhitelist() {
        String sql = "select username from test.users_whitelist";
        try {
            List<String> result = jdbcTemplate.query(sql, new SingleColumnRowMapper<>(String.class));
            log.info("Запрос на получение данных успешно выполнен");
            return result;

        } catch (DataAccessException e) {
            log.error("Ошибка запроса получения данных\n" + e.getCause().getMessage());
            return List.of();
        }
    }

    public void addToWhitelist(String username) {
        String sql = "insert into test.users_whitelist (username, is_active) values (?, ?)";
        try {
            jdbcTemplate.update(sql, username, true);
            log.info("Запись успешно добавлена в базу данных");

        } catch (DataAccessException e) {
            log.error("Ошибка добавления записи в базу данных\n" + e.getCause().getMessage());
        }
    }

    public void removeFromWhitelist(String userId) {
        String sql = "delete from test.users_whitelist where user_id = ?";
        try {
            jdbcTemplate.update(sql, Integer.parseInt(userId));
            log.info("Запись успешно удалена из базы данных");

        } catch (DataAccessException e) {
            log.error("Ошибка удаления записи \n" + e.getCause().getMessage());
        }
    }
}
