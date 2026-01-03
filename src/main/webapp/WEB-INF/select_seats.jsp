<%@ page contentType="text/html;charset=UTF-8" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="/css/cinema.css">

<html>
  <head>
    <title>Select Seats</title>
  </head>
  <body>
    <h2>Select Seats</h2>

    <form action="/book" method="post">
      <input type="hidden" name="showtimeId" value="${showtimeId}" />

      <div
        style="display: grid; grid-template-columns: repeat(10, 1fr); gap: 10px"
      >
        <c:forEach items="${seats}" var="seat">
          <label style="text-align:center">
            <input
              type="checkbox"
              name="seatIds"
              value="${seat.seatId}"
              <c:if test="${!seat.available}">disabled</c:if> />
            <span class="seat ${seat.available ? 'available' : 'occupied'}">
              ${seat.label}
            </span>
          </label>
        </c:forEach>
      </div>
      <br />
      <button type="submit">Confirm Booking</button>
    </form>
  </body>
</html>
