package com.moviebookingapp.controllers;

import com.moviebookingapp.payload.request.LoginRequest;
import com.moviebookingapp.payload.response.UserInfoResponse;
import com.moviebookingapp.security.jwt.JwtUtils;
import com.moviebookingapp.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Login implements Function<LoginRequest, UserInfoResponse> {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;


    public UserInfoResponse apply(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new UserInfoResponse(userDetails.getUsername(),
                        jwtToken,
                        roles);

    }
}
