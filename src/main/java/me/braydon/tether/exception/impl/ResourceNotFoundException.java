package me.braydon.tether.exception.impl;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is raised
 * when a resource is not found.
 *
 * @author Braydon
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(@NonNull String message) {
        super(message);
    }
}