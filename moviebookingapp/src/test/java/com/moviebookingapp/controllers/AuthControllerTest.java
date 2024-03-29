package com.moviebookingapp.controllers;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.payload.request.LoginRequest;
import com.moviebookingapp.payload.request.SignupRequest;
import com.moviebookingapp.repository.RoleRepository;
import com.moviebookingapp.repository.UserRepository;
import com.moviebookingapp.security.jwt.JwtUtils;
import com.moviebookingapp.security.services.UserDetailsImpl;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testAuthenticateUser() throws Exception {
        when(jwtUtils.generateTokenFromUsername(Mockito.<String>any())).thenReturn("12345");
        when(jwtUtils.generateJwtCookie(Mockito.<UserDetailsImpl>any())).thenReturn(null);
        when(authenticationManager.authenticate(Mockito.<Authentication>any())).thenReturn(new TestingAuthenticationToken(
                new UserDetailsImpl("yashsaxena", "yash@gmail.com", "yashsaxena", new ArrayList<>()), "Credentials"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLoginId("");
        loginRequest.setPassword("yashsaxena");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1.0/moviebooking/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testAuthenticateUser2() throws Exception {
        when(jwtUtils.generateTokenFromUsername(Mockito.<String>any())).thenReturn("12345");
        when(jwtUtils.generateJwtCookie(Mockito.<UserDetailsImpl>any())).thenReturn(null);
        when(authenticationManager.authenticate(Mockito.<Authentication>any())).thenReturn(new TestingAuthenticationToken(
                new UserDetailsImpl("yashsaxena", "yash@gmail.com", "yashsaxena", new ArrayList<>()), "Credentials"));
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1.0/moviebooking/login");
        postResult.accept("https://example.org/example");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLoginId("yashsaxena");
        loginRequest.setPassword("yashsaxena");
        String content = (new ObjectMapper()).writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder requestBuilder = postResult.contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    @Test
    void testRegisterUser() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setContactNumber("9460308234");
        signupRequest.setEmail("yash@gmail.com");
        signupRequest.setFirstName("Yash");
        signupRequest.setLastName("Saxena");
        signupRequest.setLoginId("yashsaxena");
        signupRequest.setPassword("yashsaxena");
        signupRequest.setRoles(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(signupRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1.0/moviebooking/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testRegisterUser2() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setContactNumber("9460308234");
        signupRequest.setEmail("yash1@gmail.com");
        signupRequest.setFirstName("Yash");
        signupRequest.setLastName("Saxena");
        signupRequest.setLoginId("yashsaxena1");
        signupRequest.setPassword("yashsaxena");
        signupRequest.setRoles(new HashSet<>());
        String content = (new ObjectMapper()).writeValueAsString(signupRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1.0/moviebooking/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }
}

