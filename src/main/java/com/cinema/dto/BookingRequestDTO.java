package com.cinema.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {

  @NotNull
  private Integer showtimeId;
  @NotEmpty
  private List<Integer> seatIds;

  public Integer getShowtimeId() {
    return showtimeId;
  }

  public void setShowtimeId(Integer showtimeId) {
    this.showtimeId = showtimeId;
  }

  public List<Integer> getSeatIds() {
    return seatIds;
  }

  public void setSeatIds(List<Integer> seatIds) {
    this.seatIds = seatIds;
  }
}
