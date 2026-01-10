<%@ page contentType="text/html;charset=UTF-8" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>Select Seats</title>
    <link rel="stylesheet" href="/css/cinema.css" />
  </head>
  <body>
    <h2>Select Seats</h2>

    <form action="/book" method="post">
      <input type="hidden" name="showtimeId" value="${showtimeId}" />

      <div
        style="display: grid; grid-template-columns: repeat(10, 1fr); gap: 10px"
      >
        <c:forEach items="${seats}" var="seat">
          <label style="text-align: center">
            <c:choose>
              <c:when test="${seat.available}">
                <input type="checkbox" name="seatIds" value="${seat.seatId}" />
                <span class="seat available">${seat.label}</span>
              </c:when>
              <c:otherwise>
                <input type="checkbox" disabled />
                <span class="seat occupied">${seat.label}</span>
              </c:otherwise>
            </c:choose>
          </label>
        </c:forEach>
      </div>

      <br />
      <button type="submit">Confirm Booking</button>
    </form>
  </body>
</html>
