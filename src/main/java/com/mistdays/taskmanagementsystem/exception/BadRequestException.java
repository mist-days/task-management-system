package com.mistdays.taskmanagementsystem.exception;

/**
 * Exception thrown when a request fails business validation rules.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}