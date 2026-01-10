package com.cinema.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "booking_seats", uniqueConstraints = @UniqueConstraint(columnNames = { "showtime_id", "seat_id" }))
public class BookingSeat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // RELATIONSHIPS
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  @ManyToOne(optional = false)
  @JoinColumn(name = "showtime_id", nullable = false)
  private Showtime showtime;

  @ManyToOne(optional = false)
  @JoinColumn(name = "seat_id", nullable = false)
  private Seat seat;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  // LIFECYCLE
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  public Integer getId() {
    return id;
  }

  public Booking getBooking() {
    return booking;
  }

  public Showtime getShowtime() {
    return showtime;
  }

  public Seat getSeat() {
    return seat;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  public void setShowtime(Showtime showtime) {
    this.showtime = showtime;
  }

  public void setSeat(Seat seat) {
    this.seat = seat;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
