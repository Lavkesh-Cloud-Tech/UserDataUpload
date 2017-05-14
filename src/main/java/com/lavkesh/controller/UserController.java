package com.lavkesh.controller;

import com.lavkesh.entity.User;
import com.lavkesh.services.UserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  @Autowired private UserService userService;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/user/{userId}")
  public User getUser(@PathVariable("userId") Long userId) {
    User user = userService.getUser(userId);
    return user;
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/user/dataupload")
  public Collection<User> uploadUserData() {
    return userService.uploadUserData();
  }
}
