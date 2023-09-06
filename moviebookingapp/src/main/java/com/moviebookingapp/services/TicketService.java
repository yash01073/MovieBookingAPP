package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface TicketService {

    public Ticket bookTicket(Movie movie, int numberOfTickets, List<Integer> seatNumber,String userName);

    public int findTicketsByMovieId(String movieId);

    public List<Integer> calculateAllSeatNumbersByMovieId(String movieId);


}
