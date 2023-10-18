package com.moviebookingapp.repository;


import com.moviebookingapp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByLoginId(String loginId);

  Boolean existsByLoginId(String loginId);

  Boolean existsByEmail(String email);
}
