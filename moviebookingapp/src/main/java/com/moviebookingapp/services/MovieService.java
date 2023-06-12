package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.payload.response.MovieListResponse;
import com.moviebookingapp.payload.response.TicketStatusResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MovieService {

    public List<Movie> getAllMovies();

    public List<Movie> searchMovieByPartialName(String partialName);

    public Movie findMovieByName(String movieName,String theatreName);

    public TicketStatusResponse updateTicketStatus(List<Ticket> ticketList, Movie movie);

}
