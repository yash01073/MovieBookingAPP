package com.moviebookingapp.controllers;

import com.moviebookingapp.payload.request.ChangePasswordRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import com.moviebookingapp.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class TestController {

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER')")
  public String userAccess() {
    return "User Content.";
  }

  @PostMapping("/{username}/forgot")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> userAccess(@PathVariable String username, @Valid @RequestBody ChangePasswordRequest passwordRequest) {
    if(userDetailsService.changeUserPassword(username, encoder.encode(passwordRequest.getPassword()))){
      return ResponseEntity.ok().body(new CustomResponse("0000","True","Password Changed Successfully"));
    }
    return ResponseEntity.ok().body(new CustomResponse("4001","False","Password cannot be changed"));
  }



  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
}
