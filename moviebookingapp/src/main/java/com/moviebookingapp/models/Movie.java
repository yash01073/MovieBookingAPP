package com.moviebookingapp.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "movie")
public class Movie {

    @Id
    private String id;

    private String movieName;

    private Integer ticketsAllotted;

    private Integer remainingTickets;

    private String theatreName;

    private List<Integer> bookedSeatNumbers;

    private String status;

    private String posterLink;



}
