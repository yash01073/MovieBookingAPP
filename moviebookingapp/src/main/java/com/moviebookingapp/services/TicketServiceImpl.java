package com.moviebookingapp.services;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{

    private final Logger logger = LogManager.getLogger(TicketServiceImpl.class);

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public Ticket bookTicket(Movie movie, int numberOfTickets, List<Integer> seatNumber,String userName) {
        logger.info("Inside bookTicket");
        Ticket ticket = new Ticket();
        ticket.setMovie(movie);
        ticket.setNumberOfTickets(numberOfTickets);
        ticket.setSeatNumber(seatNumber);
        ticket.setUserName(userName);
        return ticketRepository.save(ticket);

    }

    @Override
    public int findTicketsByMovieId(String movieId) {
        logger.info("Inside findTicketsByMovieId");
        List<Ticket> ticketList = ticketRepository.findByMovieId(movieId);
        int sumOfBookedTickets = 0;
        for(Ticket pass:ticketList){
            sumOfBookedTickets += pass.getNumberOfTickets();
        }
        return sumOfBookedTickets;
    }


}
