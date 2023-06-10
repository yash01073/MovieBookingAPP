package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public Ticket bookTicket(Movie movie, int numberOfTickets, List<Integer> seatNumber,String userName) {
        Ticket ticket = new Ticket();
        ticket.setMovie(movie);
        ticket.setNumberOfTickets(numberOfTickets);
        ticket.setSeatNumber(seatNumber);
        ticket.setUserName(userName);
        return ticketRepository.save(ticket);

    }
}
