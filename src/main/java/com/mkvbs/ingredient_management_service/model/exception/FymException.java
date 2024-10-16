package com.mkvbs.ingredient_management_service.model.exception;

public abstract class FymException extends  RuntimeException {
    public FymException(String message) {
        super(message);
    }
}
