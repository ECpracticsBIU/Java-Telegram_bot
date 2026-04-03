package ru.lashin.tg.databasemanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class StateDatabaseManager extends DatabaseManager {

    @Autowired
    public StateDatabaseManager(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
