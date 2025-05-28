package com.koffa.speculum_backend.common;

import com.koffa.speculum_backend.common.dto.ApiError;
import com.koffa.speculum_backend.common.exceptions.LoginFailedException;
import com.koffa.speculum_backend.common.exceptions.InvalidPasswordException;
import com.koffa.speculum_backend.common.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getLocalizedMessage()));
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPassword(InvalidPasswordException ex) {
        return ResponseEntity.status(400)
                .body(new ApiError(ex.getLocalizedMessage()));
    }
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ApiError> handleLoginFailed(LoginFailedException ex) {
        return ResponseEntity.status(401)
                .body(new ApiError(ex.getLocalizedMessage()));
    }
}
