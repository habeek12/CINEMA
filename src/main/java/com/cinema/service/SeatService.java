package com.cinema.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cinema.dto.SeatAvailabilityDTO;
import com.cinema.model.Seat;
import com.cinema.model.Showtime;
import com.cinema.repository.BookingSeatRepository;
import com.cinema.repository.SeatRepository;
import com.cinema.repository.ShowtimeRepository;

@Service
public class SeatService {

  private final SeatRepository seatRepository;
  private final BookingSeatRepository bookingSeatRepository;
  private final ShowtimeRepository showtimeRepository;

  public SeatService(
      SeatRepository seatRepository,
      BookingSeatRepository bookingSeatRepository,
      ShowtimeRepository showtimeRepository) {
    this.seatRepository = seatRepository;
    this.bookingSeatRepository = bookingSeatRepository;
    this.showtimeRepository = showtimeRepository;
  }

  public List<SeatAvailabilityDTO> getSeatsForShowtime(Integer showtimeId) {

    Showtime showtime = showtimeRepository.findById(showtimeId)
        .orElseThrow(() -> new RuntimeException("Showtime not found"));

    List<Seat> seats = seatRepository.findByTheater_IdOrderByRowLabelAscSeatNumberAsc(showtime.getTheater().getId());

    Set<Integer> occupiedSeats = new HashSet<>(
        bookingSeatRepository.findOccupiedSeatIds(showtimeId));

    return seats.stream()
        .map(seat -> new SeatAvailabilityDTO(
            seat.getId(),
            seat.getRowLabel() + seat.getSeatNumber(),
            !occupiedSeats.contains(seat.getId())))
        .collect(Collectors.toList());
  }
}
