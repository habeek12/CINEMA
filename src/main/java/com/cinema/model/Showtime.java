package com.cinema.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "showtimes")
public class Showtime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // RELATIONSHIPS
  @ManyToOne
  @JoinColumn(name = "movie_id", nullable = false)
  private Movie movie;

  @ManyToOne
  @JoinColumn(name = "theater_id", nullable = false)
  private Theater theater;

  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  @Column(name = "end_time", nullable = false)
  private LocalDateTime endTime;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  // LIFECYCLE CALLBACKS
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Movie getMovie() {
    return movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public Theater getTheater() {
    return theater;
  }

  public void setTheater(Theater theater) {
    this.theater = theater;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
