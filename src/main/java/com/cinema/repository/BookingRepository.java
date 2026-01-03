package com.cinema.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

  // Used for expiration cleanup job
  List<Booking> findByStatusAndReservedAtBefore(
      Booking.Status status,
      LocalDateTime expirationTime);
}
