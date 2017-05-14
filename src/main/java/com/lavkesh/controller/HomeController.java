package com.lavkesh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/hello")
  public String getGiddensConcept() {
    return "Hello World";
  }
}
