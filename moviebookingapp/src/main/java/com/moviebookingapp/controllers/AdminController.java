package com.moviebookingapp.controllers;

import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.UpdateStatusObject;
import com.moviebookingapp.payload.request.TicketRequest;
import com.moviebookingapp.payload.request.UpdateTicketRequest;
import com.moviebookingapp.payload.response.MessageResponse;
import com.moviebookingapp.payload.response.TicketAvailabilityResponse;
import com.moviebookingapp.services.MovieService;
import com.moviebookingapp.services.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class AdminController {

    @Autowired
    TicketService ticketService;

    @Autowired
    MovieService movieService;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @PutMapping("/updateStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketAvailabilityResponse> updateStatus(@RequestBody UpdateTicketRequest ticketRequest) {
        validateTicketRequest(ticketRequest);
        Movie movie = movieService.findMovieByName(ticketRequest.getMovieName(), ticketRequest.getTheatreName());
        UpdateStatusObject object = new UpdateStatusObject();
        int bookedTickets = ticketService.findTicketsByMovieId(movie.getId());
        object.setMovie(movie);
        object.setBookedTickets(bookedTickets);
        kafkaTemplate.send("ticket-availability-topic",object);
        TicketAvailabilityResponse response = new TicketAvailabilityResponse();
        response.setTicketsBooked(bookedTickets);
        response.setMessage("Status Updated Successfully");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{movieId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteMovie(@PathVariable String movieId){
        if(movieId==null){
            throw new MovieProcessException("Movie Id is not present");
        }
        //movieService.deleteMovieById(movieId);
        // Added delete movie with Kafka
        kafkaTemplate.send("delete-movie-topic",movieId);

        return ResponseEntity.ok().body(new MessageResponse("Movie Deleted Successfully"));
    }

    public void validateTicketRequest(UpdateTicketRequest ticketRequest){
        checkKeyAndValue("MovieName",ticketRequest.getMovieName());
        checkKeyAndValue("TheatreName",ticketRequest.getTheatreName());
    }
    public void checkKeyAndValue(String key,String value){
        if(value==null){
            throw new MovieProcessException(key+" is not found");
        }
    }

}
