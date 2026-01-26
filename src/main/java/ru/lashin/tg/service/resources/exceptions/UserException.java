package ru.lashin.tg.service.resources.exceptions;

import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataAccessException;

public class UserException extends DataAccessException {

    public UserException(@Nullable String msg) {
        super(msg);
    }
}
