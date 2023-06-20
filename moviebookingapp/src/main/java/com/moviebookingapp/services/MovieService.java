package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.payload.response.TicketAvailabilityResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MovieService {

    public List<Movie> getAllMovies();

    public List<Movie> searchMovieByPartialName(String partialName);

    public Movie findMovieByName(String movieName,String theatreName);

    public void updateTicketStatus(int sumOfBookedTickets, Movie movie);

}
