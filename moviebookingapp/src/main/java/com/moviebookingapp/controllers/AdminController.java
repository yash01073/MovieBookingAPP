/*
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

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class AdminController {

    private final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    TicketService ticketService;

    @Autowired
    MovieService movieService;

    */
/*@Autowired
    KafkaTemplate kafkaTemplate;*//*


    @PutMapping("/updateStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TicketAvailabilityResponse> updateStatus(@RequestBody UpdateTicketRequest ticketRequest) {
        logger.info("Inside updateStatus Controller");
        validateTicketRequest(ticketRequest);
        Movie movie = movieService.findMovieByName(ticketRequest.getMovieName(), ticketRequest.getTheatreName());
        UpdateStatusObject object = new UpdateStatusObject();
        int bookedTickets = ticketService.findTicketsByMovieId(movie.getId());
        List<Integer> seatNumber = ticketService.calculateAllSeatNumbersByMovieId(movie.getId());
        object.setMovie(movie);
        object.setBookedTickets(bookedTickets);
        object.setAllSeatNumbers(seatNumber);
        //kafkaTemplate.send("ticket-availability-topic",object);
        movieService.updateTicketStatus(object.getBookedTickets(), object.getMovie(),object.getAllSeatNumbers());
        TicketAvailabilityResponse response = new TicketAvailabilityResponse();
        response.setTicketsBooked(bookedTickets);
        response.setMessage("Status Updated Successfully");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{movieId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteMovie(@PathVariable String movieId){
        logger.info("Inside deleteMovie Controller");
        if(movieId==null){
            throw new MovieProcessException("Movie Id is not present");
        }
        movieService.deleteMovieById(movieId);
        //kafkaTemplate.send("delete-movie-topic",movieId);

        return ResponseEntity.ok().body(new MessageResponse("Movie Deleted Successfully"));
    }
    public void validateTicketRequest(UpdateTicketRequest ticketRequest){
        checkKeyAndValue("MovieName",ticketRequest.getMovieName());
        checkKeyAndValue("TheatreName",ticketRequest.getTheatreName());}
    public void checkKeyAndValue(String key,String value){
        if(value==null){
            throw new MovieProcessException(key+" is not found");
        }
    }

}
*/
