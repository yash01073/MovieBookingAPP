/*
package com.moviebookingapp.kafka;

import com.moviebookingapp.models.Movie;
import com.moviebookingapp.models.Ticket;
import com.moviebookingapp.models.UpdateStatusObject;
import com.moviebookingapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TicketAvailabilityConsumer {

    @Autowired
    MovieService movieService;

    @KafkaListener(topics = "ticket-availability-topic",groupId = "group_id",containerFactory = "kafkaLister")
    public void consumeTicketAvailability(UpdateStatusObject object) {
        // Update movie ticket availability status in the movie table


        // Perform the update operation in the movie table using a repository or service method
        movieService.updateTicketStatus(object.getBookedTickets(), object.getMovie(), object.getAllSeatNumbers());
    }

    */
/*@KafkaListener(topics = "delete-movie-topic",groupId = "group_id",containerFactory = "kafkaLister")
    public void consumeDeleteMovie(String movieId) {
        // Delete Movie from table


        // Perform the delete operation in the movie table using a repository or service method
        movieService.deleteMovieById(movieId);
    }*//*


}
*/
