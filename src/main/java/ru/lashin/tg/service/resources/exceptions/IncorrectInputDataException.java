package ru.lashin.tg.service.resources.exceptions;

import org.jspecify.annotations.Nullable;

public class IncorrectInputDataException extends UserException {

    public IncorrectInputDataException(@Nullable String msg) {
        super(msg);
    }
}
