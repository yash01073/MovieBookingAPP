package com.moviebookingapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.payload.request.UpdateTicketRequest;
import com.moviebookingapp.services.MovieService;
import com.moviebookingapp.services.TicketService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private KafkaTemplate kafkaTemplate;

    @MockBean
    private MovieService movieService;

    @MockBean
    private TicketService ticketService;

    
    @Test
    void testDeleteMovie() throws Exception {
        doNothing().when(movieService).deleteMovieById(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1.0/moviebooking/delete/{movieId}", "42");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Movie Deleted Successfully\"}"));
    }

    @Test
    void testUpdateStatus() throws Exception {
        when(ticketService.findTicketsByMovieId(Mockito.<String>any())).thenReturn(1);

        Movie movie = new Movie();
        movie.setId("12345");
        movie.setMovieName("Avengers");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Rajmandir");
        movie.setTicketsAllotted(10);
        doNothing().when(movieService).updateTicketStatus(anyInt(), Mockito.<Movie>any());
        when(movieService.findMovieByName(Mockito.<String>any(), Mockito.<String>any())).thenReturn(movie);

        UpdateTicketRequest updateTicketRequest = new UpdateTicketRequest();
        updateTicketRequest.setMovieName("Avengers");
        updateTicketRequest.setTheatreName("Rajmandir");
        String content = (new ObjectMapper()).writeValueAsString(updateTicketRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1.0/moviebooking/updateStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"ticketsBooked\":1,\"message\":\"Status Updated Successfully\"}"));
    }

    @Test
    void testValidateTicketRequest() {
        UpdateTicketRequest ticketRequest = new UpdateTicketRequest();
        ticketRequest.setMovieName("Avengers");
        ticketRequest.setTheatreName("Rajmandir");
        adminController.validateTicketRequest(ticketRequest);
        assertEquals("Avengers", ticketRequest.getMovieName());
        assertEquals("Rajmandir", ticketRequest.getTheatreName());
    }

    @Test
    void testCheckKeyAndValue() {
        assertThrows(MovieProcessException.class, () -> adminController.checkKeyAndValue("Key", null));
    }
}

