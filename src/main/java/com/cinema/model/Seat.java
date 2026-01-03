package com.cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "seats", uniqueConstraints = @UniqueConstraint(columnNames = { "theater_id", "row_label",
    "seat_number" }))
public class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  // Relationships
  @ManyToOne(optional = false)
  @JoinColumn(name = "theater_id")
  private Theater theater;

  @Column(name = "row_label", nullable = false)
  private String rowLabel;

  @Column(name = "seat_number", nullable = false)
  private Integer seatNumber;

  @Column(name = "seat_type")
  private String seatType;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Theater getTheater() {
    return theater;
  }

  public void setTheater(Theater theater) {
    this.theater = theater;
  }

  public String getRowLabel() {
    return rowLabel;
  }

  public void setRowLabel(String rowLabel) {
    this.rowLabel = rowLabel;
  }

  public Integer getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(Integer seatNumber) {
    this.seatNumber = seatNumber;
  }

  public String getSeatType() {
    return seatType;
  }

  public void setSeatType(String seatType) {
    this.seatType = seatType;
  }
}
