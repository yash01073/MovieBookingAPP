package com.moviebookingapp.repository;


import com.moviebookingapp.models.ERole;
import com.moviebookingapp.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
