package com.moviebookingapp.controllers;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.payload.request.TicketRequest;
import com.moviebookingapp.security.jwt.JwtUtils;
import com.moviebookingapp.security.services.UserDetailsServiceImpl;
import com.moviebookingapp.services.MovieService;
import com.moviebookingapp.services.TicketService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MovieController.class})
@ExtendWith(SpringExtension.class)
class MovieControllerTest {
    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    private MovieController movieController;

    @MockBean
    private MovieService movieService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Method under test: {@link MovieController#bookTicket(String, TicketRequest)}
     */
    @Test
    void testBookTicket() throws Exception {
        when(jwtUtils.getUserNameFromJwtToken(Mockito.<String>any())).thenReturn("janedoe");

        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(10);
        when(movieService.findMovieByName(Mockito.<String>any(), Mockito.<String>any())).thenReturn(movie);

        Movie movie2 = new Movie();
        movie2.setId("43");
        movie2.setMovieName("Movie Name");
        movie2.setRemainingTickets(1);
        movie2.setStatus("Status");
        movie2.setTheatreName("Theatre Name");
        movie2.setTicketsAllotted(10);



        Ticket ticket = new Ticket();
        ticket.setId("42");
        ticket.setMovie(movie2);
        ticket.setNumberOfTickets(7);
        ticket.setSeatNumber(new ArrayList<>(List.of(1,2,3,4,5,6,7)));
        ticket.setUserName("janedoe");
        when(ticketService.bookTicket(Mockito.<Movie>any(), anyInt(), Mockito.<List<Integer>>any(), Mockito.<String>any()))
                .thenReturn(ticket);

        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setMovieName("Movie Name");
        ticketRequest.setNumberOfSeats(3);
        ticketRequest.setSeatNumbers(new ArrayList<>(List.of(8,9,10)));
        ticketRequest.setTheatreName("Theatre Name");
        String content = (new ObjectMapper()).writeValueAsString(ticketRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1.0/moviebooking/add")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"success\":\"True\",\"code\":\"0000\",\"message\":\"Ticket Booked Successfully\",\"errorMessage\":null}"));
    }

    /**
     * Method under test: {@link MovieController#getMoviesList()}
     */
    @Test
    void testGetMoviesList() throws Exception {
        when(movieService.getAllMovies()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1.0/moviebooking/all");
        MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"movieList\":[]}"));
    }



    /**
     * Method under test: {@link MovieController#searchMovie(String)}
     */
    @Test
    void testSearchMovie() throws Exception {
        when(movieService.searchMovieByPartialName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1.0/moviebooking/movies/search/{movieName}", "Movie Name");
        MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"movieList\":[]}"));
    }


}

