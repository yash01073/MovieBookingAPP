package com.moviebookingapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TicketServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TicketServiceImplTest {
    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    /**
     * Method under test: {@link TicketServiceImpl#bookTicket(Movie, int, List, String)}
     */
    @Test
    void testBookTicket() {
        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(1);

        Ticket ticket = new Ticket();
        ticket.setId("42");
        ticket.setMovie(movie);
        ticket.setNumberOfTickets(10);
        ticket.setSeatNumber(new ArrayList<>());
        ticket.setUserName("janedoe");
        when(ticketRepository.save(Mockito.<Ticket>any())).thenReturn(ticket);

        Movie movie2 = new Movie();
        movie2.setId("42");
        movie2.setMovieName("Movie Name");
        movie2.setRemainingTickets(1);
        movie2.setStatus("Status");
        movie2.setTheatreName("Theatre Name");
        movie2.setTicketsAllotted(1);
        assertSame(ticket, ticketServiceImpl.bookTicket(movie2, 10, new ArrayList<>(), "janedoe"));
        verify(ticketRepository).save(Mockito.<Ticket>any());
    }

    /**
     * Method under test: {@link TicketServiceImpl#findTicketsByMovieId(String)}
     */
    @Test
    void testFindTicketsByMovieId() {
        when(ticketRepository.findByMovieId(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertEquals(0, ticketServiceImpl.findTicketsByMovieId("42"));
        verify(ticketRepository).findByMovieId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link TicketServiceImpl#findTicketsByMovieId(String)}
     */
    @Test
    void testFindTicketsByMovieId2() {
        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(1);

        Ticket ticket = new Ticket();
        ticket.setId("42");
        ticket.setMovie(movie);
        ticket.setNumberOfTickets(10);
        ticket.setSeatNumber(new ArrayList<>());
        ticket.setUserName("janedoe");

        ArrayList<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        when(ticketRepository.findByMovieId(Mockito.<String>any())).thenReturn(ticketList);
        assertEquals(10, ticketServiceImpl.findTicketsByMovieId("42"));
        verify(ticketRepository).findByMovieId(Mockito.<String>any());
    }
}

