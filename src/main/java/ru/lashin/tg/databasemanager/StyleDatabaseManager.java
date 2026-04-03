package ru.lashin.tg.databasemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Slf4j
@Repository
public class StyleDatabaseManager extends DatabaseManager {

    @Autowired
    public StyleDatabaseManager(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public String getStyle(String name) {
        String sql = "select config_json from test.styles_config where name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, name);
        } catch (EmptyResultDataAccessException e) {
            log.info("Запрос на получение данных style выполнен");
            return "";
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на получение данных style: \n{}", e.getMessage());
            throw e;
        }
    }

    public List<String> getAllStyles() {
        String sql = "select name from test.styles_config";
        try {
            return jdbcTemplate.queryForList(sql, String.class);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на получение списка данных style: \n{}", e.getMessage());
            throw e;
        }
    }


    public void addStyle(String name, String data) {
        String sql = "insert into test.styles_config (name, config_json) values (?, ?::jsonb)";
        try {
            jdbcTemplate.update(sql, name, data);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на добавление данных style: \n{}", e.getMessage());
            throw e;
        }
    }

    public int removeStyle(String name) {
        String sql = "delete from test.styles_config where name = ?";
        try {
            return jdbcTemplate.update(sql, name);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на удаление данных style: \n{}", e.getMessage());
            throw e;
        }
    }
}
