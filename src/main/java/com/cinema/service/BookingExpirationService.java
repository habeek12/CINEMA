package com.cinema.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cinema.repository.BookingRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingExpirationService {
  private final BookingRepository bookingRepository;

  public BookingExpirationService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  @Scheduled(fixedRate = 60000) // Cada 60 segundos
  @Transactional
  public void expirePendingBookings() {
    int expired = bookingRepository.expireOldBookings();

    if (expired > 0) {
      System.out.println("Expired bookings: " + expired);
    }
  }
}
