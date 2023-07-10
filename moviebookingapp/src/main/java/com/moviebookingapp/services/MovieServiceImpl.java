package com.moviebookingapp.services;

import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

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
        return movieRepository.findByMovieNameLike(".*" + partialName + ".*");
    }

    @Override
    public Movie findMovieByName(String movieName,String theatreName){
        return movieRepository.findByMovieNameAndTheatreName(movieName,theatreName);
    }

    @Override
    public void updateTicketStatus(int sumOfBookedTickets,Movie movie){
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
        if(movieRepository.findById(movieId).isPresent()){
            mongoTemplate.remove(new Query(Criteria.where("id").is(movieId)), Movie.class);
        }else{
            throw new MovieProcessException("Error in Movie Id");
        }
    }
}
