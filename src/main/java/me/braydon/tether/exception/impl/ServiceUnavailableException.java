package me.braydon.tether.exception.impl;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is raised when a
 * service is unavailable. Such as
 * when trying to interact with Discord
 * and the bot is not connected.
 *
 * @author Braydon
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public final class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(@NonNull String message) {
        super(message);
    }
}