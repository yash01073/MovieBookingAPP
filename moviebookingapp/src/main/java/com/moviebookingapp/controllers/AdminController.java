package com.moviebookingapp.controllers;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.UpdateStatusObject;
import com.moviebookingapp.payload.request.UpdateTicketRequest;
import com.moviebookingapp.payload.response.TicketAvailabilityResponse;
import com.moviebookingapp.services.MovieService;
import com.moviebookingapp.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    public ResponseEntity<TicketAvailabilityResponse> adminAccess(@RequestBody UpdateTicketRequest ticketRequest) {
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

}
