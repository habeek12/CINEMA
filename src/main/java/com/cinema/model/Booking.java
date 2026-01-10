package com.cinema.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

  // ENUM
  public enum Status {
    PENDING,
    CONFIRMED,
    CANCELLED,
    EXPIRED
  }

  // FIELDS
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // RELATIONSHIPS
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "showtime_id", nullable = false)
  private Showtime showtime;

  @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<BookingSeat> seats = new ArrayList<>();

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;

  @Column(nullable = false, length = 5)
  private String currency = "MXN";

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private Status status;

  @Column(name = "booking_reference", unique = true)
  private String bookingReference;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "reserved_at")
  private LocalDateTime reservedAt;

  @Column(name = "cancelled_at")
  private LocalDateTime cancelledAt;

  @Column(name = "expires_at")
  private LocalDateTime expiresAt;

  // LIFECYCLE
  @PrePersist
  void onCreate() {
    if (currency == null) {
      this.currency = "MXN";
    }
    if (createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }

  public Integer getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Showtime getShowtime() {
    return showtime;
  }

  public void setShowtime(Showtime showtime) {
    this.showtime = showtime;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getCurrency() {
    return currency;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getBookingReference() {
    return bookingReference;
  }

  public void setBookingReference(String bookingReference) {
    this.bookingReference = bookingReference;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getReservedAt() {
    return reservedAt;
  }

  public void setReservedAt(LocalDateTime reservedAt) {
    this.reservedAt = reservedAt;
  }

  public List<BookingSeat> getSeats() {
    return seats;
  }

  public void setSeats(List<BookingSeat> seats) {
    this.seats = seats;
  }

  public LocalDateTime getCancelledAt() {
    return cancelledAt;
  }

  public void setCancelledAt(LocalDateTime cancelledAt) {
    this.cancelledAt = cancelledAt;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

  public void setCreatedAt(LocalDateTime now) {
    this.createdAt = now;
  }
}
