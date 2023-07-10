package com.moviebookingapp.exceptions;

import lombok.Getter;

@Getter
public class MovieProcessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public String code;

    public MovieProcessException(String message){super(message);}

    public MovieProcessException(String code, String message){super(message);
        this.code = code;
    }
}
