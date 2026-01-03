package com.cinema.dto;

import java.util.List;

public class BookingRequestDTO {

  private Integer showtimeId;
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
