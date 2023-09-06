package com.moviebookingapp.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.moviebookingapp.exceptions.MovieProcessException;
import com.moviebookingapp.models.Movie;
import com.moviebookingapp.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MovieServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MovieServiceImplTest {
    @MockBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MovieServiceImpl movieServiceImpl;


    @Test
    void testGetAllMovies() {
        ArrayList<Movie> movieList = new ArrayList<>();
        when(movieRepository.findAll()).thenReturn(movieList);
        List<Movie> actualAllMovies = movieServiceImpl.getAllMovies();
        assertSame(movieList, actualAllMovies);
        assertTrue(actualAllMovies.isEmpty());
        verify(movieRepository).findAll();
    }


    @Test
    void testGetAllMoviesNegative() {
        when(movieRepository.findAll()).thenThrow(new MovieProcessException("An error occurred"));
        assertThrows(MovieProcessException.class, () -> movieServiceImpl.getAllMovies());
        verify(movieRepository).findAll();
    }


    @Test
    void testSearchMovieByPartialName() {
        ArrayList<Movie> movieList = new ArrayList<>();
        when(movieRepository.findByMovieNameLike(Mockito.<String>any())).thenReturn(movieList);
        List<Movie> actualSearchMovieByPartialNameResult = movieServiceImpl.searchMovieByPartialName("Partial Name");
        assertSame(movieList, actualSearchMovieByPartialNameResult);
        assertTrue(actualSearchMovieByPartialNameResult.isEmpty());
        verify(movieRepository).findByMovieNameLike(Mockito.<String>any());
    }


    @Test
    void testSearchMovieByPartialNameNegative() {
        when(movieRepository.findByMovieNameLike(Mockito.<String>any()))
                .thenThrow(new MovieProcessException("An error occurred"));
        assertThrows(MovieProcessException.class, () -> movieServiceImpl.searchMovieByPartialName("Partial Name"));
        verify(movieRepository).findByMovieNameLike(Mockito.<String>any());
    }


    @Test
    void testFindMovieByName() {
        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(1);
        when(movieRepository.findByMovieNameAndTheatreName(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(movie);
        assertSame(movie, movieServiceImpl.findMovieByName("Movie Name", "Theatre Name"));
        verify(movieRepository).findByMovieNameAndTheatreName(Mockito.<String>any(), Mockito.<String>any());
    }


    @Test
    void testFindMovieByNameNegative() {
        when(movieRepository.findByMovieNameAndTheatreName(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new MovieProcessException("An error occurred"));
        assertThrows(MovieProcessException.class, () -> movieServiceImpl.findMovieByName("Movie Name", "Theatre Name"));
        verify(movieRepository).findByMovieNameAndTheatreName(Mockito.<String>any(), Mockito.<String>any());
    }


    @Test
    void testUpdateTicketStatus() {
        when(mongoTemplate.updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any())).thenReturn(null);
        List<Integer> bookedSeats = new ArrayList<>();
        bookedSeats.add(1);
        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(1);
        movieServiceImpl.updateTicketStatus(1, movie,bookedSeats);
        verify(mongoTemplate).updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any());
    }




    @Test
    void testUpdateTicketStatusNegative() {
        when(mongoTemplate.updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any())).thenThrow(new MovieProcessException("An error occurred"));
        List<Integer> bookedSeats = new ArrayList<>();
        bookedSeats.add(1);
        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(1);
        assertThrows(MovieProcessException.class, () -> movieServiceImpl.updateTicketStatus(1, movie,bookedSeats));
        verify(mongoTemplate).updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(),
                Mockito.<Class<Object>>any());
    }


    @Test
    void testDeleteMovieById() {
        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(1);
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(mongoTemplate.remove(Mockito.<Query>any(), Mockito.<Class<Object>>any())).thenReturn(null);
        movieServiceImpl.deleteMovieById("42");
        verify(movieRepository).findById(Mockito.<String>any());
        verify(mongoTemplate).remove(Mockito.<Query>any(), Mockito.<Class<Object>>any());
    }


    @Test
    void testDeleteMovieByIdNegative() {
        Movie movie = new Movie();
        movie.setId("42");
        movie.setMovieName("Movie Name");
        movie.setRemainingTickets(1);
        movie.setStatus("Status");
        movie.setTheatreName("Theatre Name");
        movie.setTicketsAllotted(1);
        Optional<Movie> ofResult = Optional.of(movie);
        when(movieRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(mongoTemplate.remove(Mockito.<Query>any(), Mockito.<Class<Object>>any()))
                .thenThrow(new MovieProcessException("An error occurred"));
        assertThrows(MovieProcessException.class, () -> movieServiceImpl.deleteMovieById("42"));
        verify(movieRepository).findById(Mockito.<String>any());
        verify(mongoTemplate).remove(Mockito.<Query>any(), Mockito.<Class<Object>>any());
    }


}

