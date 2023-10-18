package com.moviebookingapp.controllers;

import com.moviebookingapp.models.User;
import com.moviebookingapp.payload.request.LoginRequest;
import com.moviebookingapp.payload.response.MovieListResponse;
import com.moviebookingapp.payload.response.UserInfoResponse;
import com.moviebookingapp.repository.UserRepository;
import com.moviebookingapp.security.jwt.JwtUtils;
import com.moviebookingapp.security.services.UserDetailsImpl;
import com.moviebookingapp.security.services.UserDetailsServiceImpl;
import com.moviebookingapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Movies implements Function<String,MovieListResponse> {

    @Autowired
    MovieService movieService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserRepository userRepository;

    public MovieListResponse apply(String token) {
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            User user = userRepository.findByLoginId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getName().toString())
                    .collect(Collectors.toList());
            MovieListResponse movieListResponse = new MovieListResponse();
            if(roles.contains("ROLE_USER")){
                movieListResponse.setMovieList(movieService.getAllMovies());
                movieListResponse.setCode("200");
            }else{
                movieListResponse.setCode("400");
            }
            return movieListResponse;
        }else{
            MovieListResponse movieListResponse = new MovieListResponse();
            movieListResponse.setCode("400");
            return movieListResponse;
        }

    }
}
