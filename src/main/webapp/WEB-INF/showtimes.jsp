<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ page
contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Showtimes</title>
  </head>
  <body>
    <h2>Showtimes</h2>

    <ul>
      <c:forEach items="${showtimes}" var="s">
        <li>
          ${s.startTime} - ${s.theater.name}
          <a href="/select-seats?showtimeId=${s.id}">Select seats</a>
        </li>
      </c:forEach>
    </ul>
  </body>
</html>
