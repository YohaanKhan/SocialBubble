package com.socialapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * <p>
 * This class uses {@link ControllerAdvice} to act as a centralized
 * component for handling exceptions thrown by any controller. It ensures that
 * user-friendly, structured error responses are returned.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions thrown when a {@code @Valid} annotated argument fails validation.
     * <p>
     * This method catches {@link MethodArgumentNotValidException} and transforms the
     * validation errors into a structured JSON map, mapping the field name to its
     * corresponding error message.
     *
     * @param ex The {@link MethodArgumentNotValidException} instance containing the validation errors.
     * @return A {@link ResponseEntity} containing a map of validation errors and an
     * HTTP {@code 400 BAD_REQUEST} status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions (MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->
                errors.put(error.getObjectName(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other unhandled exceptions as a last resort.
     * <p>
     * This generic exception handler acts as a safety net, catching any exception that
     * does not have a more specific handler. It prevents sensitive stack trace information
     * from being exposed to the client and provides a generic error message.
     *
     * @param ex The generic {@link Exception} that was caught.
     * @return A {@link ResponseEntity} containing a generic error message and an
     * HTTP {@code 500 INTERNAL_SERVER_ERROR} status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericExceptions (Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
