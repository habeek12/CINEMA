package com.cinema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinema.model.Showtime;
import com.cinema.repository.ShowtimeRepository;

@Service
public class ShowtimeService {

  private final ShowtimeRepository showtimeRepository;

  public ShowtimeService(ShowtimeRepository showtimeRepository) {
    this.showtimeRepository = showtimeRepository;
  }

  public List<Showtime> getShowtimesByMovie(Integer movieId) {
    return showtimeRepository.findByMovie_IdOrderByStartTime(movieId);
  }

  public Showtime getById(Integer id) {
    return showtimeRepository.findById(id).orElse(null);
  }
}
