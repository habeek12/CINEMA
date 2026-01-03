package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
  boolean existsByBooking_Id(Integer bookingId);
}
