package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.payload.response.MovieListResponse;
import com.moviebookingapp.payload.response.TicketStatusResponse;
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
    public TicketStatusResponse updateTicketStatus(List<Ticket> ticketList,Movie movie){
        int seatsVacant,sumOfBookedTickets= 0;
        for(Ticket pass:ticketList){
            sumOfBookedTickets += pass.getNumberOfTickets();
        }
        seatsVacant = movie.getTicketsAllotted()-sumOfBookedTickets;
        String status = (seatsVacant==0)?"SOLD_OUT":"BOOK_ASAP";
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(movie.getId()));
        Update update = new Update();
        update.set("status", status);
        update.set("ticketsAllotted",seatsVacant);
        mongoTemplate.updateFirst(query, update, Movie.class);
        //Movie updatedMovie = movieRepository.updateMovieStatusAndTicketsAllotted(movie.getId(),status,seatsVacant);
        TicketStatusResponse ticketStatusResponse = new TicketStatusResponse(ticketList, seatsVacant, status);
        return ticketStatusResponse;

    }
}
