package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.service.SeatService;

@Controller
public class SeatController {

  private final SeatService seatService;

  public SeatController(SeatService seatService) {
    this.seatService = seatService;
  }

  @GetMapping("/select-seats")
  public String selectSeats(
      @RequestParam Integer showtimeId,
      Model model) {

    model.addAttribute("seats", seatService.getSeatsForShowtime(showtimeId));
    model.addAttribute("showtimeId", showtimeId);
    return "select_seats";
  }
}
