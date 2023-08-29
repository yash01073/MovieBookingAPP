package com.moviebookingapp.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTicketRequest {

    private String movieName;

    private String theatreName;

}
