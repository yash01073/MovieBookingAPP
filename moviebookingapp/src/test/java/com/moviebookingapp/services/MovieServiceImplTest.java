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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    /**
     * Method under test: {@link MovieServiceImpl#getAllMovies()}
     */
    @Test
    void testGetAllMovies() {
        ArrayList<Movie> movieList = new ArrayList<>();
        when(movieRepository.findAll()).thenReturn(movieList);
        List<Movie> actualAllMovies = movieServiceImpl.getAllMovies();
        assertSame(movieList, actualAllMovies);
        assertTrue(actualAllMovies.isEmpty());
        verify(movieRepository).findAll();
    }

    /**
     * Method under test: {@link MovieServiceImpl#searchMovieByPartialName(String)}
     */
    @Test
    void testSearchMovieByPartialName() {
        ArrayList<Movie> movieList = new ArrayList<>();
        when(movieRepository.findByMovieNameLike(Mockito.<String>any())).thenReturn(movieList);
        List<Movie> actualSearchMovieByPartialNameResult = movieServiceImpl.searchMovieByPartialName("Partial Name");
        assertSame(movieList, actualSearchMovieByPartialNameResult);
        assertTrue(actualSearchMovieByPartialNameResult.isEmpty());
        verify(movieRepository).findByMovieNameLike(Mockito.<String>any());
    }

    /**
     * Method under test: {@link MovieServiceImpl#searchMovieByPartialName(String)}
     */
    @Test
    void testSearchMovieByPartialName2() {
        when(movieRepository.findByMovieNameLike(Mockito.<String>any()))
                .thenThrow(new MovieProcessException("An error occurred"));
        assertThrows(MovieProcessException.class, () -> movieServiceImpl.searchMovieByPartialName("Partial Name"));
        verify(movieRepository).findByMovieNameLike(Mockito.<String>any());
    }

    /**
     * Method under test: {@link MovieServiceImpl#findMovieByName(String, String)}
     */
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

    /**
     * Method under test: {@link MovieServiceImpl#findMovieByName(String, String)}
     */
    @Test
    void testFindMovieByName2() {
        when(movieRepository.findByMovieNameAndTheatreName(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new MovieProcessException("An error occurred"));
        assertThrows(MovieProcessException.class, () -> movieServiceImpl.findMovieByName("Movie Name", "Theatre Name"));
        verify(movieRepository).findByMovieNameAndTheatreName(Mockito.<String>any(), Mockito.<String>any());
    }


}

