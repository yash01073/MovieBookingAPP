package com.moviebookingapp.controllers;

import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.models.User;
import com.moviebookingapp.payload.request.TicketRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import com.moviebookingapp.payload.response.MovieListResponse;
import com.moviebookingapp.repository.UserRepository;
import com.moviebookingapp.security.jwt.JwtUtils;
import com.moviebookingapp.services.MovieService;
import com.moviebookingapp.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Book implements Function<TicketRequest,CustomResponse> {

    @Autowired
    MovieService movieService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketService ticketService;

    public CustomResponse apply(TicketRequest ticketRequest) {
        String token = ticketRequest.getToken();
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            User user = userRepository.findByLoginId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.toList());
            if(ticketRequest.getMovieName()==null){
                return new CustomResponse("400", "False", "MovieName is not found");
            }
            if(ticketRequest.getTheatreName()==null){
                return new CustomResponse("400", "False", "TheatreName is not found");
            }
            if (ticketRequest.getSeatNumbers() == null || ticketRequest.getSeatNumbers().isEmpty()
                    || ticketRequest.getSeatNumbers().size() != ticketRequest.getNumberOfSeats()) {
                return new CustomResponse("400", "False", "Seat Numbers entered are invalid");
            }
            Movie movie = movieService.findMovieByName(ticketRequest.getMovieName(), ticketRequest.getTheatreName());
            if(roles.contains("ROLE_USER")){
                if (movie != null) {
                    Ticket ticket = ticketService.bookTicket(movie, ticketRequest.getNumberOfSeats(), ticketRequest.getSeatNumbers(), username);
                    if (ticket != null) {
                        return new CustomResponse("200", "True", "Ticket Booked Successfully");
                    } else {
                        return new CustomResponse("400", "False", "Ticket Booking Failed");
                    }
                }else{
                    return new CustomResponse("400","False","Invalid Movie");
                }
            }else{
                return new CustomResponse("400","False","Invalid User");
            }
        }else{
            return new CustomResponse("400","False","Invalid User");
        }
    }
}
