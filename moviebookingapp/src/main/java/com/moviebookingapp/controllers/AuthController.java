package com.moviebookingapp.controllers;


import com.moviebookingapp.exceptions.UserDetailsException;
import com.moviebookingapp.models.ERole;
import com.moviebookingapp.models.Role;
import com.moviebookingapp.models.User;
import com.moviebookingapp.payload.request.LoginRequest;
import com.moviebookingapp.payload.request.SignupRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import com.moviebookingapp.payload.response.MessageResponse;
import com.moviebookingapp.payload.response.UserInfoResponse;
import com.moviebookingapp.repository.RoleRepository;
import com.moviebookingapp.repository.UserRepository;
import com.moviebookingapp.security.jwt.JwtUtils;
import com.moviebookingapp.security.services.UserDetailsImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class AuthController {

  private final Logger logger = LogManager.getLogger(AuthController.class);
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    logger.info("Inside autheticateUser Controller");
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

    ResponseCookie cookie = jwtUtils.generateJwtCookie(userDetails);

    String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, String.valueOf(cookie))
        .body(new UserInfoResponse(userDetails.getUsername(),
                                   jwtToken,
                                   roles));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    logger.info("Inside registerUser Controller");
    if (userRepository.existsByLoginId(signUpRequest.getLoginId())) {
      return ResponseEntity
          .badRequest()
          .body(new UserDetailsException("Login ID is already in use"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new UserDetailsException("Email ID is already in use"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getLoginId(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()),
                         signUpRequest.getFirstName(),
                         signUpRequest.getLastName(),
                         signUpRequest.getContactNumber());

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new UserDetailsException("Role is not found"));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new UserDetailsException("Role is not found"));
          roles.add(adminRole);

        case "user":
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
               .orElseThrow(() -> new UserDetailsException("Role is not found"));
          roles.add(userRole);

        default:
              userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new UserDetailsException("Role is not found"));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new CustomResponse("0000","True","User Registered Successfully"));
  }
}
