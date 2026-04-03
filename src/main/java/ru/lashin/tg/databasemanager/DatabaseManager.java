package ru.lashin.tg.databasemanager;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Менеджер взаимодействия с базой данных.
 */
public abstract class DatabaseManager {

    protected final JdbcTemplate jdbcTemplate;

    public DatabaseManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
