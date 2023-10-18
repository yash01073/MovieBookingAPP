package com.moviebookingapp.controllers;


import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.moviebookingapp.payload.request.TicketRequest;
import com.moviebookingapp.payload.request.UpdateTicketRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.util.Optional;

public class UpdateHandler extends FunctionInvoker<UpdateTicketRequest, CustomResponse> {

    @FunctionName("update")
    public HttpResponseMessage execute(
            @HttpTrigger(name = "request", methods = {HttpMethod.PUT}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<UpdateTicketRequest>> request,
            ExecutionContext context) {
        if (request.getBody().isPresent()) {
            UpdateTicketRequest ticketRequest = request.getBody().get();
            ticketRequest.setToken(request.getHeaders().get("authorization").substring(7));
            CustomResponse customResponse = handleRequest(ticketRequest, context);
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
