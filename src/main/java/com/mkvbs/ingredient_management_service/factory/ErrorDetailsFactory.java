package com.mkvbs.ingredient_management_service.factory;

import com.mkvbs.ingredient_management_service.exception.ErrorDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ErrorDetailsFactory {

    public ErrorDetails createNotFound(String resource) {
        return new ErrorDetails(
                LocalDateTime.now(),
                "404",
                resource);
    }

    public ErrorDetails createBadRequest(String resource) {
        return new ErrorDetails(
                LocalDateTime.now(),
                "400",
                resource);
    }

    public ErrorDetails createNotAcceptable(String resource) {
        return new ErrorDetails(
                LocalDateTime.now(),
                "406",
                resource);
    }
}
