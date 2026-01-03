package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinema.dto.BookingRequestDTO;
import com.cinema.model.Payment;
import com.cinema.model.User;
import com.cinema.service.BookingService;
import com.cinema.service.PaymentService;
import com.cinema.service.UserService;

@Controller
@RequestMapping
public class BookingController {

  private final UserService userService;
  private final BookingService bookingService;
  private final PaymentService paymentService;

  public BookingController(
      UserService userService,
      BookingService bookingService,
      PaymentService paymentService) {

    this.userService = userService;
    this.bookingService = bookingService;
    this.paymentService = paymentService;
  }

  @GetMapping("/bookings")
  public String myBookings(Model model) {
    model.addAttribute("bookings", bookingService.getAll());
    return "bookings";
  }

  @PostMapping("/book")
  public String bookSeats(@ModelAttribute BookingRequestDTO dto) {
    User user = userService.findByUsername("juan"); // TODO: Replace with authenticated user (Spring Security)
    bookingService.createBooking(user, dto);
    return "redirect:/bookings";
  }

  @PostMapping("/pay")
  public String payBooking(
      @RequestParam Integer bookingId,
      @RequestParam Payment.Method method) {

    paymentService.payBooking(bookingId, method);
    return "redirect:/bookings";
  }
}
