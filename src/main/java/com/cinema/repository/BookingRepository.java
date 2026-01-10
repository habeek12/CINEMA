package com.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cinema.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

  @Modifying
  @Query("""
      UPDATE Booking b
      SET b.status = 'EXPIRED'
      WHERE b.status = 'PENDING'
      AND b.expiresAt < CURRENT_TIMESTAMP
      """)
  int expireOldBookings();

  List<Booking> findByUser_Id(Integer userId);

}
