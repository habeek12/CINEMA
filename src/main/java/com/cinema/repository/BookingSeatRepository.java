package com.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinema.model.Booking;
import com.cinema.model.BookingSeat;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {

  boolean existsByShowtime_IdAndSeat_IdAndBooking_StatusIn(
      Integer showtimeId,
      Integer seatId,
      List<Booking.Status> statuses);

  @Query("""
          SELECT bs.seat.id
          FROM BookingSeat bs
          WHERE bs.showtime.id = :showtimeId
          AND bs.booking.status IN ('PENDING', 'CONFIRMED')
      """)
  List<Integer> findOccupiedSeatIds(@Param("showtimeId") Integer showtimeId);

}
