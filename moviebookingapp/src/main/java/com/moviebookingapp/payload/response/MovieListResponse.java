package com.moviebookingapp.payload.response;

import com.moviebookingapp.models.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieListResponse {

    private List<Movie> movieList;
}
