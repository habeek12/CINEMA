package com.cinema.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cinema.dto.BookingRequestDTO;
import com.cinema.exception.SeatUnavailableException;
import com.cinema.model.Booking;
import com.cinema.model.BookingSeat;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.model.User;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.BookingSeatRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final BookingSeatRepository bookingSeatRepository;
  private final ShowtimeRepository showtimeRepository;
  private final SeatRepository seatRepository;

  public BookingService(
      BookingRepository bookingRepository,
      BookingSeatRepository bookingSeatRepository,
      ShowtimeRepository showtimeRepository,
      SeatRepository seatRepository) {
    this.bookingRepository = bookingRepository;
    this.bookingSeatRepository = bookingSeatRepository;
    this.showtimeRepository = showtimeRepository;
    this.seatRepository = seatRepository;
  }

  @Transactional
  public Booking createBooking(User user, BookingRequestDTO dto) {

    Showtime showtime = showtimeRepository.findById(dto.getShowtimeId())
        .orElseThrow(() -> new RuntimeException("Showtime not found"));

    List<Seat> seats = seatRepository.findAllById(dto.getSeatIds());

    Booking booking = new Booking();
    booking.setUser(user);
    booking.setShowtime(showtime);
    booking.setBookingReference(UUID.randomUUID().toString());
    booking.setSeats(new ArrayList<>());

    BigDecimal total = BigDecimal.ZERO;

    for (Seat seat : seats) {
      boolean occupied = bookingSeatRepository
          .existsByShowtime_IdAndSeat_Id(showtime.getId(), seat.getId());

      if (occupied) {
        throw new SeatUnavailableException(
            "Seat " + seat.getRowLabel() + seat.getSeatNumber() + " is already booked");
      }

      BookingSeat bs = new BookingSeat();
      bs.setBooking(booking);
      bs.setShowtime(showtime);
      bs.setSeat(seat);
      bs.setPrice(showtime.getPrice());

      booking.getSeats().add(bs);
      total = total.add(showtime.getPrice());
    }

    booking.setTotalPrice(total);
    return bookingRepository.save(booking);

  }

  public List<Booking> getAll() {
    return bookingRepository.findAll();
  }

}
