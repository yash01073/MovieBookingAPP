package com.moviebookingapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class User {

  public User(String loginId, String email, String password, String firstName, String lastName, String contactNumber) {
    this.loginId = loginId;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.contactNumber = contactNumber;
  }

  @Id
  @NotBlank
  @Size(max = 20)
  private String loginId;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @NotBlank
  @Size(min = 3, max = 20)
  private String firstName;

  @NotBlank
  @Size(min = 3, max = 20)
  private String lastName;

  @NotBlank
  @Size(min = 10, max = 10)
  private String contactNumber;

  @DBRef
  private Set<Role> roles = new HashSet<>();

  public User() {
  }


}
