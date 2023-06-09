package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.payload.response.MovieListResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MovieService {

    public List<Movie> getAllMovies();

    public List<Movie> searchMovieByPartialName(String partialName);

}
