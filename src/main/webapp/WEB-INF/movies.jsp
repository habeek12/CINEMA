<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Cinema Please - Movies</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h2>Available Movies</h2>

<div class="row">
    <c:forEach items="${movies}" var="movie">
  <div class="col-md-4 mb-3">
    <div class="card">
      <img src="${movie.posterUrl}" class="card-img-top">
      <div class="card-body">
        <h5>${movie.title}</h5>
        <p>${movie.genre}</p>
        <a href="/showtimes?movieId=${movie.id}" class="btn btn-primary">
          View Showtimes
        </a>
      </div>
    </div>
  </div>
</c:forEach>

</div>

</body>
</html>
