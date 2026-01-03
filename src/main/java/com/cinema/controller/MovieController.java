package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cinema.service.MovieService;

@Controller
public class MovieController {
  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public String listMovies(Model model) {
    model.addAttribute("movies", movieService.getActiveMovies());
    return "movies";
  }
}
