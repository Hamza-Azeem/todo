package com.todo.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Error> handleDuplicateResourceException(DuplicateResourceException ex){
        return new ResponseEntity<>(new Error(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(new Error(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Error> handleInvalidTokenException(InvalidTokenException ex){
        return new ResponseEntity<>(new Error(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED);
    }

}
