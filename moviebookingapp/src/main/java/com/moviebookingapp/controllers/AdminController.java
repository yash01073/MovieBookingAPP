package com.moviebookingapp.controllers;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.payload.request.UpdateTicketRequest;
import com.moviebookingapp.payload.response.TicketStatusResponse;
import com.moviebookingapp.services.MovieService;
import com.moviebookingapp.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class AdminController {

    @Autowired
    TicketService ticketService;

    @Autowired
    MovieService movieService;

    @PutMapping("/admin")
    public ResponseEntity<TicketStatusResponse> adminAccess(@RequestBody UpdateTicketRequest ticketRequest) {
        Movie movie = movieService.findMovieByName(ticketRequest.getMovieName(), ticketRequest.getTheatreName());
        return ResponseEntity.ok().body(movieService.updateTicketStatus(ticketService.findTicketsByMovieId(movie.getId()),movie));
    }

}
