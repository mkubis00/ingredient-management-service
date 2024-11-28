package com.mkvbs.ingredient_management_service.exception;

import com.mkvbs.ingredient_management_service.factory.ErrorDetailsFactory;
import com.mkvbs.ingredient_management_service.model.exception.EntityAlreadyExistsException;
import com.mkvbs.ingredient_management_service.model.exception.EntityNotFoundException;
import com.mkvbs.ingredient_management_service.resource.api.ValidationMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
public class MainExceptionHandler {

    private final ErrorDetailsFactory detailsFactory;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );
        StringBuilder validationError = new StringBuilder();
        for (Map.Entry<String, String> entry : validationErrors.entrySet()) {
            validationError.append(entry.getKey()).append(" - ").append(entry.getValue()).append(". ");
        }
        ErrorDetails errorDetails = detailsFactory.createBadRequest("Invalid arguments: " + validationError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailsFactory.createBadRequest(ValidationMessage.HTTP_MESSAGE_NOT_READABLE));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailsFactory.createNotFound(ex.getMessage()));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(detailsFactory.createNotAcceptable(ex.getMessage()));
    }
}
