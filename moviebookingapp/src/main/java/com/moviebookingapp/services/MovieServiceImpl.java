package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.payload.response.MovieListResponse;
import com.moviebookingapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> searchMovieByPartialName(String partialName) {
        return movieRepository.findByMovieNameLike(".*" + partialName + ".*");
    }

    @Override
    public Movie findMovieByName(String movieName,String theatreName){
        return movieRepository.findByMovieNameAndTheatreName(movieName,theatreName);
    }
}
