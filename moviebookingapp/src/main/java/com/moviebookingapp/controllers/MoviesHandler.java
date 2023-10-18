package com.moviebookingapp.controllers;


import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.moviebookingapp.payload.request.LoginRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import com.moviebookingapp.payload.response.MovieListResponse;
import com.moviebookingapp.payload.response.UserInfoResponse;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.util.Optional;

public class MoviesHandler extends FunctionInvoker<String, MovieListResponse>{

    @FunctionName("movies")
    public HttpResponseMessage execute(
            @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<?>> request,
            ExecutionContext context) {
        String token = request.getHeaders().get("authorization").substring(7);
        MovieListResponse response = handleRequest(token, context);
        if(response.getCode().equals("200")){
            return request.createResponseBuilder(HttpStatus.OK)
                    .body(response)
                    .header("Content-Type", "application/json")
                    .build();
        }else{
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body(response)
                    .header("Content-Type", "application/json")
                    .build();
        }

    }
}
