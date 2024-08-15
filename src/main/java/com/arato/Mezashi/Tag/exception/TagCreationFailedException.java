package com.arato.Mezashi.Tag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TagCreationFailedException extends RuntimeException {
    public TagCreationFailedException(String message) {
        super(message);
    }
}
