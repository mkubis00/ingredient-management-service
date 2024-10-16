package com.mkvbs.ingredient_management_service.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {

    private final LocalDateTime timestamp;
    private final String details;

    public ErrorDetails(final String details) {
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}
