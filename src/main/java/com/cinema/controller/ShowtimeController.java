package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.service.ShowtimeService;

@Controller
public class ShowtimeController {

  private final ShowtimeService showtimeService;

  public ShowtimeController(ShowtimeService showtimeService) {
    this.showtimeService = showtimeService;
  }

  @GetMapping("/showtimes")
  public String showtimes(
      @RequestParam Integer movieId,
      Model model) {

    model.addAttribute("showtimes",
        showtimeService.getShowtimesByMovie(movieId));
    return "showtimes";
  }
}
