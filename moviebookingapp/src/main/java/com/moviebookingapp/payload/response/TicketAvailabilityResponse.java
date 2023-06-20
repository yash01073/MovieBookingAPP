package com.moviebookingapp.payload.response;

import com.moviebookingapp.models.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TicketAvailabilityResponse {


    private int ticketsBooked;

    private String message;

}
