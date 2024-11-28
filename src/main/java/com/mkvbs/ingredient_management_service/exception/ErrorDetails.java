package com.mkvbs.ingredient_management_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private final LocalDateTime timestamp;
    private final String status;
    private final String error;
}
