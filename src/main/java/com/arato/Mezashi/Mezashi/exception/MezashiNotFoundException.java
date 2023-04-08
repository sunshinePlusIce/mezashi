package com.arato.Mezashi.Mezashi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MezashiNotFoundException extends RuntimeException {
    public MezashiNotFoundException(String message) {
        super(message);
    }
}
