package com.moviebookingapp.exceptions;

import lombok.Getter;

@Getter
public class UserDetailsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public String code;

    public UserDetailsException(String message){super(message);}

    public UserDetailsException(String code,String message){super(message);
        this.code = code;
    }
}
