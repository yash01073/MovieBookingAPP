package com.moviebookingapp.controllers;

import com.moviebookingapp.exceptions.UserDetailsException;
import com.moviebookingapp.models.ERole;
import com.moviebookingapp.models.Role;
import com.moviebookingapp.models.User;
import com.moviebookingapp.payload.request.SignupRequest;
import com.moviebookingapp.payload.response.CustomResponse;
import com.moviebookingapp.repository.RoleRepository;
import com.moviebookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;


@Component
public class Signup implements Function<SignupRequest, CustomResponse> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;


    public CustomResponse apply(SignupRequest signupRequest) {

        if (userRepository.existsByLoginId(signupRequest.getLoginId())) {
            return new CustomResponse("400","False","Login ID is already in use");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new CustomResponse("400","False","Email ID is already in use");
        }
        // Create new user's account
        User user = new User(signupRequest.getLoginId(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getContactNumber());

        Set<String> strRoles = signupRequest.getRoles();
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
        return new CustomResponse("200","True","User Registered Successfully");
    }
}
