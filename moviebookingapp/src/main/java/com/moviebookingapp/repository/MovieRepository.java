package com.moviebookingapp.repository;

import com.moviebookingapp.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    @Query("{'movieName': { $regex: ?0, $options: 'i' }}")
    List<Movie> findByMovieNameLike(String movieName);

    Movie findByMovieNameAndTheatreName(String movieName, String theatreName);

    Optional<Movie> findById(String id);

}
