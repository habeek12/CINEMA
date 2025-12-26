package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/movies")
  public String movies() {
    return "movies";
  }

}
