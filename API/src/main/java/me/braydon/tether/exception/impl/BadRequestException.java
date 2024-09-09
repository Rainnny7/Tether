package me.braydon.tether.exception.impl;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is raised
 * when a bad request is made.
 *
 * @author Braydon
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class BadRequestException extends RuntimeException {
    public BadRequestException(@NonNull String message) {
        super(message);
    }
}