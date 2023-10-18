package com.moviebookingapp.controllers;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.models.UpdateStatusObject;
import com.moviebookingapp.models.User;
import com.moviebookingapp.payload.request.TicketRequest;
import com.moviebookingapp.payload.request.UpdateTicketRequest;
import com.moviebookingapp.payload.response.CustomResponse;
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
public class Update implements Function<UpdateTicketRequest,CustomResponse> {

    @Autowired
    MovieService movieService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketService ticketService;

    public CustomResponse apply(UpdateTicketRequest ticketRequest) {
        String token = ticketRequest.getToken();
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            User user = userRepository.findByLoginId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.toList());
            if (ticketRequest.getMovieName() == null) {
                return new CustomResponse("400", "False", "MovieName is not found");
            }
            if (ticketRequest.getTheatreName() == null) {
                return new CustomResponse("400", "False", "TheatreName is not found");
            }
            Movie movie = movieService.findMovieByName(ticketRequest.getMovieName(), ticketRequest.getTheatreName());
            if(roles.contains("ROLE_ADMIN")) {
                UpdateStatusObject object = new UpdateStatusObject();
                int bookedTickets = ticketService.findTicketsByMovieId(movie.getId());
                List<Integer> seatNumber = ticketService.calculateAllSeatNumbersByMovieId(movie.getId());
                object.setMovie(movie);
                object.setBookedTickets(bookedTickets);
                object.setAllSeatNumbers(seatNumber);
                //kafkaTemplate.send("ticket-availability-topic",object);
                movieService.updateTicketStatus(object.getBookedTickets(), object.getMovie(), object.getAllSeatNumbers());
                return new CustomResponse("200", "True", bookedTickets + " Tickets Updated");
            }else{
                return new CustomResponse("400","False","Invalid User");
            }
        } else {
            return new CustomResponse("400", "False", "Invalid User");
        }
    }
}
