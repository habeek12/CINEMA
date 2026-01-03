<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>My Bookings</h2>

<c:forEach items="${bookings}" var="b">
  <div>
    <p>Booking Ref: ${b.bookingReference}</p>
    <p>Status: ${b.status}</p>
    <p>Total: $${b.totalPrice}</p>

    <c:if test="${b.status == 'PENDING'}">
      <form action="/pay" method="post">
        <input type="hidden" name="bookingId" value="${b.id}" />
        <select name="method">
          <option value="CARD">Card</option>
          <option value="CASH">Cash</option>
          <option value="PAYPAL">PayPal</option>
          <option value="TRANSFER">Transfer</option>
        </select>
        <button type="submit">Pay</button>
      </form>
    </c:if>
  </div>
  <hr />
</c:forEach>
