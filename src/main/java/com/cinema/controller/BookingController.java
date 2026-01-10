package com.cinema.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/booking")
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

  private User getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return userService.findByUsername(auth.getName());
  }

  @GetMapping("/bookings")
  public String myBookings(Model model) {
    User user = getCurrentUser();
    model.addAttribute("bookings", bookingService.getBookingsForUser(user));
    return "bookings";
  }

  @PostMapping("/book")
  public String bookSeats(
      @Valid @ModelAttribute BookingRequestDTO dto,
      BindingResult result) {

    if (result.hasErrors()) {
      return "booking-form";
    }

    User user = getCurrentUser();
    bookingService.createBooking(user, dto);
    return "redirect:/booking/bookings";
  }

  @PostMapping("/pay")
  public String payBooking(
      @RequestParam Integer bookingId,
      @RequestParam Payment.Method method) {

    paymentService.payBooking(bookingId, method);
    return "redirect:/booking/bookings";
  }

  @PostMapping("/cancel")
  public String cancelBooking(@RequestParam Integer bookingId) {
    User user = getCurrentUser();
    bookingService.cancelBooking(bookingId, user);
    return "redirect:/booking/bookings";
  }
}
