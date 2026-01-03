package com.cinema.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "booking_id", nullable = false)
  private Booking booking;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Method method;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status = Status.PENDING;

  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  // ENUMS
  public enum Method {
    CARD,
    CASH,
    PAYPAL,
    TRANSFER
  }

  public enum Status {
    PENDING,
    PAID,
    FAILED,
    REFUNDED
  }

  // JPA CALLBACKS
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

  public BigDecimal getAmount() {
    return amount;
  }

  public Method getMethod() {
    return method;
  }

  public Status getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
