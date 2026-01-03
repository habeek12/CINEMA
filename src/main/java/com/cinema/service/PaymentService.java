package com.cinema.service;

import org.springframework.stereotype.Service;

import com.cinema.model.Booking;
import com.cinema.model.Payment;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final BookingRepository bookingRepository;

  public PaymentService(
      PaymentRepository paymentRepository,
      BookingRepository bookingRepository) {
    this.paymentRepository = paymentRepository;
    this.bookingRepository = bookingRepository;
  }

  @Transactional
  public void payBooking(
      Integer bookingId,
      Payment.Method method) {
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new RuntimeException("Booking not found"));
    if (paymentRepository.existsById(bookingId)) {
      throw new RuntimeException("Payment already exists for this booking");
    }

    if (!booking.getStatus().equals(Booking.Status.PENDING)) {
      throw new RuntimeException("Booking already paid or cancelled");
    }

    Payment payment = new Payment();
    payment.setBooking(booking);
    payment.setAmount(booking.getTotalPrice());
    payment.setMethod(method);
    payment.setStatus(Payment.Status.PAID);
    paymentRepository.save(payment);

    booking.setStatus(Booking.Status.CONFIRMED);
    booking.setReservedAt(java.time.LocalDateTime.now());
    bookingRepository.save(booking);
  }
}
