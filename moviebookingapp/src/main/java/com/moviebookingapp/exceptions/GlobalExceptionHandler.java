package com.moviebookingapp.exceptions;


import com.mongodb.MongoException;
import com.moviebookingapp.exceptions.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserDetailsException.class)
    protected ResponseEntity<Object> handleUserDetailsException(UserDetailsException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(LocalDateTime.now(), ex.getCode(),ex.getMessage(),details);
    return new ResponseEntity<>(apiError,BAD_REQUEST);
    }

    @ExceptionHandler(MovieProcessException.class)
    protected ResponseEntity<Object> handleMovieProcessException(MovieProcessException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(LocalDateTime.now(), ex.getCode(),ex.getMessage(),details);
        return new ResponseEntity<>(apiError,BAD_REQUEST);
    }

    @ExceptionHandler(MongoException.class)
    protected ResponseEntity<Object> handleMongoDBException(MongoException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(LocalDateTime.now(), "5001",ex.getMessage(),details);
        return new ResponseEntity<>(apiError,INTERNAL_SERVER_ERROR);
    }

}
