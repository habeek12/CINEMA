package com.cinema.dto;

public class SeatAvailabilityDTO {

  private Integer seatId;
  private String label;
  private boolean available;

  public SeatAvailabilityDTO(Integer seatId, String label, boolean available) {
    this.seatId = seatId;
    this.label = label;
    this.available = available;
  }

  public Integer getSeatId() {
    return seatId;
  }

  public String getLabel() {
    return label;
  }

  public boolean isAvailable() {
    return available;
  }
}
