package com.moviebookingapp.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;
    private int numberOfTickets;
    private List<Integer> seatNumber;
    private String userName;

    @DBRef
    private Movie movie; // Reference to the "Movie" table




}
