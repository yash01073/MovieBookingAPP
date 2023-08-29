package com.moviebookingapp.services;

import com.moviebookingapp.controllers.MovieController;
import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.repository.MovieRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    private final Logger logger = LogManager.getLogger(MovieServiceImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> searchMovieByPartialName(String partialName) {
        logger.info("Inside searchMovieByPartialName");
        return movieRepository.findByMovieNameLike(".*" + partialName + ".*");
    }

    @Override
    public Movie findMovieByName(String movieName,String theatreName){
        logger.info("Inside findMovieByName");
        return movieRepository.findByMovieNameAndTheatreName(movieName,theatreName);
    }

    @Override
    public void updateTicketStatus(int sumOfBookedTickets,Movie movie){
        logger.info("Inside updateTicketStatus");
        Integer seatsVacant;
        if(sumOfBookedTickets+movie.getRemainingTickets()==movie.getTicketsAllotted()){
            seatsVacant = movie.getRemainingTickets();
        }else{
            seatsVacant = movie.getTicketsAllotted()-sumOfBookedTickets;
        }
        String status = (seatsVacant==0)?"SOLD_OUT":"BOOK_ASAP";
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(movie.getId()));
        Update update = new Update();
        update.set("status", status);
        update.set("remainingTickets",seatsVacant);
        mongoTemplate.updateFirst(query, update, Movie.class);

    }

    @Override
    public void deleteMovieById(String movieId){
        logger.info("Inside deleteMovieById");
        if(movieRepository.findById(movieId).isPresent()){
            mongoTemplate.remove(new Query(Criteria.where("id").is(movieId)), Movie.class);
        }else{
            throw new MovieProcessException("Error in Movie Id");
        }
    }
}
