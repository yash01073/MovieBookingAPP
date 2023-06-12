package com.moviebookingapp.repository;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    @Query("{'movieName': { $regex: ?0, $options: 'i' }}")
    List<Movie> findByMovieNameLike(String movieName);

    Movie findByMovieNameAndTheatreName(String movieName, String theatreName);


}
