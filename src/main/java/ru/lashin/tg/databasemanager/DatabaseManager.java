package ru.lashin.tg.databasemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Менеджер взаимодействия с базой данных.
 */
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
            return jdbcTemplate.queryForObject(sql, String.class, name);
        } catch (EmptyResultDataAccessException e) {
            log.info("Запрос на получение данных style выполнен");
            return "";
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на получение данных style: \n{}", e.getMessage());
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

    public List<String> getWhitelist() {
        String sql = "select username from test.users_whitelist";
        try {
            return jdbcTemplate.queryForList(sql, String.class);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на получение данных whitelist: \n{}", e.getMessage());
            throw e;
        }
    }

    public int addToWhitelist(String username) {
        String sql = "insert into test.users_whitelist (username, is_active) values (?, ?)";
        try {
            return jdbcTemplate.update(sql, username, true);


        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на добавление данных whitelist: \n{}", e.getMessage());
            throw e;
        }
    }

    public int removeFromWhitelist(Integer userId) {
        String sql = "delete from test.users_whitelist where user_id = ?";
        try {
            return jdbcTemplate.update(sql, userId);
        } catch (DataAccessException e) {
            log.error("Техническая ошибка при выполнении запроса на удаление данных whitelist: \n{}", e.getMessage());
            throw e;
        }
    }

    public boolean containsUserId(Long userId) {
        String sql = "select exists(select 1 from test.admin_ids where admin_id = ?);";
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, userId));
        } catch (DataAccessException e) {
            log.error("Ошибка доступа к списку администраторов \n{}", e.getMessage());
        }
        return false;
    }

    public void addChatIdToStateMemory(Long chatId, String state) {
        String sql = "insert into test.chat_state (chat_id, state) values (?, ?) " +
                "on conflict (chat_id) do update set state = EXCLUDED.state;";
        try {
            jdbcTemplate.update(sql, chatId, state);
            log.info("Запись chat_state создана");
        } catch (DataAccessException e) {
            log.error("Ошибка добавления записи chat_state\n{}", e.getMessage());
        }

    }

    public void removeChatIdFromStateMemory(Long chatId) {
        String sql = "delete from test.chat_state where chat_id = ?";
        try {
            jdbcTemplate.update(sql, chatId);
            log.info("Запись chat_state удалена");
        } catch (DataAccessException e) {
            log.error("Ошибка запроса удаления данных state\n{}", e.getMessage());
        }

    }

    public String getChatState(Long chatId) {
        String sql = "select state from test.chat_state where chat_id = ?";
        try {
            String result = jdbcTemplate.queryForObject(sql, String.class, chatId);
            log.info("Запрос на получение данных state успешно выполнен");
            return result;
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
        catch (DataAccessException e) {
            log.error("Ошибка при запросе получения данных state\n{}", e.getMessage());
            return "";
        }

    }
}
