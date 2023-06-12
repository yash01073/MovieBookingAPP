package com.moviebookingapp.payload.response;

import com.moviebookingapp.models.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TicketStatusResponse {


    private List<Ticket> numberOfTickets;

    private int numberOfTicketsAvailable;

    private String ticketStatus;

}
