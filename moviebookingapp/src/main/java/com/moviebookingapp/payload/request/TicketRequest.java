package com.moviebookingapp.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketRequest {

    private String movieName;

    private String theatreName;

    private int numberOfSeats;

    private List<Integer> seatNumbers;




}
