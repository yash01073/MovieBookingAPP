package com.moviebookingapp.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.payload.request.ChangePasswordRequest;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @MockBean
    private KafkaTemplate kafkaTemplate;
    private


    @Test
    void testBookTicket() throws Exception {
        when(jwtUtils.getUserNameFromJwtToken(Mockito.<String>any())).thenReturn("12345");

        Movie movie = new Movie();
        movie.setId("12345");
        movie.setMovieName("Avengers");
        movie.setTheatreName("Rajmandir");
        when(movieService.findMovieByName(Mockito.<String>any(), Mockito.<String>any())).thenReturn(movie);


        Ticket ticket = new Ticket();
        ticket.setId("12345");
        ticket.setMovie(movie);
        ticket.setNumberOfTickets(10);
        ticket.setSeatNumber(new ArrayList<>());
        ticket.setUserName("yashsaxena");
        when(ticketService.bookTicket(Mockito.<Movie>any(), anyInt(), Mockito.<List<Integer>>any(), Mockito.<String>any()))
                .thenReturn(ticket);

        ArrayList<Integer> seatNumbers = new ArrayList<>();
        seatNumbers.add(2);
        seatNumbers.add(8);
        seatNumbers.add(10);

        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setMovieName("Avengers");
        ticketRequest.setNumberOfSeats(3);
        ticketRequest.setSeatNumbers(seatNumbers);
        ticketRequest.setTheatreName("Rajmandir");
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

    @Test
    void testBookTicket2() throws Exception {
        when(jwtUtils.getUserNameFromJwtToken(Mockito.<String>any())).thenReturn("Ticket Booked Successfully");

        Movie movie = new Movie();
        movie.setId("12345");
        movie.setMovieName("Avengers");
        movie.setTheatreName("Theatre Name");
        when(movieService.findMovieByName(Mockito.<String>any(), Mockito.<String>any())).thenReturn(movie);

        Ticket ticket = new Ticket();
        ticket.setId("12345");
        ticket.setMovie(movie);
        ticket.setNumberOfTickets(3);
        ticket.setSeatNumber(new ArrayList<>());
        ticket.setUserName("yashsaxena");
        when(
                ticketService.bookTicket(Mockito.<Movie>any(), anyInt(), Mockito.<List<Integer>>any(), Mockito.<String>any()))
                .thenReturn(ticket);

        ArrayList<Integer> seatNumbers = new ArrayList<>();
        seatNumbers.add(2);
        seatNumbers.add(8);
        seatNumbers.add(10);

        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setMovieName("Avengers");
        ticketRequest.setNumberOfSeats(3);
        ticketRequest.setSeatNumbers(seatNumbers);
        ticketRequest.setTheatreName("Rajmandir");
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

    /*@Test
    void testValidateTicketRequest() {
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setMovieName("Avengers");
        ticketRequest.setNumberOfSeats(10);
        ticketRequest.setSeatNumbers(new ArrayList<>());
        ticketRequest.setTheatreName("Rajmandir");
        assertThrows(MovieProcessException.class, () -> movieController.validateTicketRequest(ticketRequest));
    }*/


    @Test
    void testChangePassword() throws Exception {
        when(userDetailsServiceImpl.changeUserPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setPassword("yashsaxena");
        String content = (new ObjectMapper()).writeValueAsString(changePasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1.0/moviebooking/{username}/forgot", "yashsaxena")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"success\":\"True\",\"code\":\"0000\",\"message\":\"Password Changed Successfully\",\"errorMessage\":null}"));
    }

    @Test
    void testChangePasswordNegative() throws Exception {
        when(userDetailsServiceImpl.changeUserPassword(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setPassword("");
        String content = (new ObjectMapper()).writeValueAsString(changePasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1.0/moviebooking/{username}/forgot", "yashsaxena")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

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

