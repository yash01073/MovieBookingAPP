package com.moviebookingapp.exceptions;


import com.mongodb.MongoException;
import com.moviebookingapp.payload.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserDetailsException.class)
    protected CustomResponse handleUserDetailsException(UserDetailsException ex){
        CustomResponse customResponse = new CustomResponse("9999","False",ex.getMessage());
        //ApiError apiError = new ApiError(LocalDateTime.now(), ex.getCode(),ex.getMessage(),details);
        return customResponse;
    }

    @ExceptionHandler(MovieProcessException.class)
    protected CustomResponse handleMovieProcessException(MovieProcessException ex){
        //ApiError apiError = new ApiError(LocalDateTime.now(), ex.getCode(),ex.getMessage(),details);
        CustomResponse customResponse = new CustomResponse("9999","False",ex.getMessage());
        return customResponse;
    }

    @ExceptionHandler(MongoException.class)
    protected ApiError handleMongoDBException(MongoException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError apiError = new ApiError(LocalDateTime.now(), "5001",ex.getMessage(),details);
        MultiValueMap<String, Object> header = CollectionUtils.toMultiValueMap(Map.of("Content-Type", List.of("application/json")));
        return apiError;
    }

}
