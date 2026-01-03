package com.cinema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> getActiveMovies() {
    return movieRepository.findByActiveTrue();
  }

  public Movie getById(Integer id) {
    return movieRepository.findById(id).orElse(null);
  }
}
