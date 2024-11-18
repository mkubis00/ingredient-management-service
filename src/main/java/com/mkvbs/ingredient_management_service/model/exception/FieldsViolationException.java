package com.mkvbs.ingredient_management_service.model.exception;

public class FieldsViolationException extends RuntimeException {
    public FieldsViolationException(String message) {
        super(message);
    }
}