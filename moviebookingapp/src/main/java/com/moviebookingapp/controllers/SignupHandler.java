package com.moviebookingapp.controllers;


import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.moviebookingapp.payload.request.SignupRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.util.Optional;

public class SignupHandler extends FunctionInvoker<SignupRequest, CustomResponse> {

    @FunctionName("signup")
    public HttpResponseMessage execute(
            @HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<SignupRequest>> request,
            ExecutionContext context) {
        if (request.getBody().isPresent()) {
            SignupRequest signupRequest = request.getBody().get();
            CustomResponse customResponse = handleRequest(signupRequest, context);
            if (customResponse.getCode().equals("200")) {
                return request.createResponseBuilder(HttpStatus.OK)
                        .body(customResponse)
                        .header("Content-Type", "application/json")
                        .build();
            } else {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body(customResponse)
                        .header("Content-Type", "application/json")
                        .build();
            }
        }else{
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body(new CustomResponse("400","False","Request Body Error"))
                    .header("Content-Type", "application/json")
                    .build();
        }
    }
}
