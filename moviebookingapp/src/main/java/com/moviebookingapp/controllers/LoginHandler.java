package com.moviebookingapp.controllers;


import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.moviebookingapp.payload.request.LoginRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import com.moviebookingapp.payload.response.UserInfoResponse;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class LoginHandler extends FunctionInvoker<LoginRequest, UserInfoResponse>{

    @FunctionName("login")
    public HttpResponseMessage execute(
            @HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<LoginRequest>> request,
            ExecutionContext context) {
        if (request.getBody().isPresent()) {
            LoginRequest loginRequest = request.getBody().get();
            return request.createResponseBuilder(HttpStatus.OK)
                    .body(handleRequest(loginRequest, context))
                    .header("Content-Type", "application/json")
                    .build();
        }else{
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body(new CustomResponse("400", "False", "Request Body Error"))
                    .header("Content-Type", "application/json")
                    .build();
        }
    }
}
