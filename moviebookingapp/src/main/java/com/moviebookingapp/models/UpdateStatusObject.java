package com.moviebookingapp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateStatusObject {

    private int bookedTickets;

    private Movie movie;

    private List<Integer> allSeatNumbers;

}
