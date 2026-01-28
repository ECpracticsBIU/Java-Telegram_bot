package ru.lashin.tg.service.resources.exceptions;

import org.jspecify.annotations.Nullable;

public class NotFoundDataException extends UserException {

    public NotFoundDataException(@Nullable String msg) {
        super(msg);
    }
}
