package com.moviebookingapp.controllers;

import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.payload.request.ChangePasswordRequest;
import com.moviebookingapp.payload.request.TicketRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import com.moviebookingapp.payload.response.MovieListResponse;
import com.moviebookingapp.security.jwt.JwtUtils;
import com.moviebookingapp.security.services.UserDetailsServiceImpl;
import com.moviebookingapp.services.MovieService;
import com.moviebookingapp.services.TicketService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class MovieController {

  private final Logger logger = LogManager.getLogger(MovieController.class);

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  private MovieService movieService;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  TicketService ticketService;

  @Autowired
  private JwtUtils jwtUtils;


  @PostMapping("/{username}/forgot")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> changePassword(@RequestHeader(name = "Authorization") String authToken,@PathVariable String username, @Valid @RequestBody ChangePasswordRequest passwordRequest) {
    logger.info("Inside Password change Controller");
    String token = authToken.substring(7);
    String loggedUsername = jwtUtils.getUserNameFromJwtToken(token);
    if(!StringUtils.equals(username,loggedUsername)){
      throw new MovieProcessException("Username is not correct");
    }
    if(userDetailsService.changeUserPassword(username, encoder.encode(passwordRequest.getPassword()))){
      return ResponseEntity.ok().body(new CustomResponse("0000","True","Password Changed Successfully"));
    }else
    throw new MovieProcessException("Password cannot be Changed");
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<MovieListResponse> getMoviesList(){
    logger.info("Inside getMoviesList Controller");
    MovieListResponse movieListResponse = new MovieListResponse();
    movieListResponse.setMovieList(movieService.getAllMovies());
    return ResponseEntity.ok().body(movieListResponse);
  }

  @GetMapping("movies/search/{movieName}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<MovieListResponse> searchMovie(@PathVariable String movieName){
    logger.info("Inside searchMovie Controller");
    if(movieName==null){
      throw new MovieProcessException("Movie name is not present");
    }
    MovieListResponse movieListResponse = new MovieListResponse();
    movieListResponse.setMovieList(movieService.searchMovieByPartialName(movieName));
    return ResponseEntity.ok().body(movieListResponse);
  }

  @PostMapping("/add")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> bookTicket(@RequestHeader(name = "Authorization") String authToken,@RequestBody TicketRequest ticketRequest) {
    logger.info("Inside bookTicket Controller");
    if(ticketRequest!=null){
      validateTicketRequest(ticketRequest);
    }else{
      throw new MovieProcessException("Input valid Ticket Request");
    }

    String token = authToken.substring(7);
    String username = jwtUtils.getUserNameFromJwtToken(token);
    if (username != null) {
      Movie movie = movieService.findMovieByName(ticketRequest.getMovieName(), ticketRequest.getTheatreName());
      if (movie != null) {
        Ticket ticket = ticketService.bookTicket(movie, ticketRequest.getNumberOfSeats(), ticketRequest.getSeatNumbers(),username);
        if (ticket != null) {
          return ResponseEntity.ok().body(new CustomResponse("0000","True","Ticket Booked Successfully"));
        }
      } else {
        throw new MovieProcessException("Movie not Valid");
      }
    } else {
      throw new MovieProcessException("User Not Found");
    }
    throw new MovieProcessException("Some Error Occurred");
  }

  public void validateTicketRequest(TicketRequest ticketRequest){
    checkKeyAndValue("MovieName",ticketRequest.getMovieName());
    checkKeyAndValue("TheatreName",ticketRequest.getTheatreName());
    if(ticketRequest.getSeatNumbers() == null || ticketRequest.getSeatNumbers().isEmpty()
    || ticketRequest.getSeatNumbers().size()!=ticketRequest.getNumberOfSeats()){
      throw new MovieProcessException("Seat Numbers entered are invalid");
    }

  }
  public void checkKeyAndValue(String key,String value){
    if(value==null){
      throw new MovieProcessException(key+" is not found");
    }
  }
}
