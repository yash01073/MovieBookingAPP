package com.moviebookingapp.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse {

    private String success;

    private String code;

    public CustomResponse(String code, String success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    private String message;

    private String errorMessage;

    public CustomResponse() {

    }
}
