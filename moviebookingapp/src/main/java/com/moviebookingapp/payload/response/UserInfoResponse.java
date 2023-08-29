package com.moviebookingapp.payload.response;

import java.util.List;

public class UserInfoResponse {

  private String username;
  private String access_token;
  private List<String> roles;

  public UserInfoResponse( String username, String access_token, List<String> roles) {

    this.username = username;
    this.access_token = access_token;
    this.roles = roles;
  }
  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
