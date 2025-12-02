<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
</header>


<!-- Danh sách trận đấu dùng JSTL -->
<main class="container">
<h1 class="title">Trận đấu sắp diễn ra</h1>


<div class="matches">
<c:forEach var="m" items="${matches}">
<div class="match-card">
<img src="${m.image}" class="match-banner" />
<div class="match-info">
<h2>${m.home} vs ${m.away}</h2>
<p><strong>Ngày:</strong> ${m.date}</p>
<p><strong>Giờ:</strong> ${m.time}</p>
<p><strong>Sân:</strong> ${m.stadium}</p>
<a href="booking.jsp?id=${m.id}" class="btn">Đặt Vé</a>
</div>
</div>
</c:forEach>
</div>
</main>


<footer class="footer">
Copyright © 2025 CAHN FC Ticketing
</footer>
</body>
</html>