package com.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Integer> {

  List<Showtime> findByMovie_IdOrderByStartTime(Integer movieId);
}
