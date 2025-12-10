<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ - HAGL Tickets</title>
<style>
/* CSS ĐÃ ĐƯỢC DỌN DẸP */
.match-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
	gap: 20px;
	margin-top: 30px;
}

.match-card {
	overflow: hidden;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding-bottom: 10px;
}

.image-container {
	padding-top: 56.25%;
	height: 0;
	position: relative;
	overflow: hidden;
}

.image-container img {
	width: 100%;
	height: 100%;
	position: absolute;
	top: 0;
	left: 0;
	object-fit: cover;
}

.hagl_banner {
	width: 100%;

	height: 600px;
	background-image: url('resources/hagl_banner.jpg');

	background-size: cover;
	background-repeat: no-repeat;
	background-position: center;
	border-radius: 8px;
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" />

	<main style="max-width: 1200px; margin: 20px auto; padding: 0 20px;">

		<div class="hagl_banner"></div>

		<h2>Trận đấu sắp diễn ra</h2>

		<c:choose>
			<c:when test="${not empty errorMessage}">
				<p style="color: red; font-weight: bold;">LỖI HỆ THỐNG:
					${errorMessage}</p>
				<p>Vui lòng kiểm tra Console Server (Tomcat) để biết chi tiết.</p>
			</c:when>

			<c:when test="${not empty matchList}">

				<p style="color: blue;">Đã tìm thấy ${fn:length(matchList)} trận
					đấu.</p>

				<div class="match-grid">
					<c:forEach var="match" items="${matchList}">
						<a href="matchDetail?id=${match.matchId}" class="match-link">
							<div class="match-card">

								<div class="image-container">
									<img src="resources/stadium_placeholder.jpg"
										alt="Sân vận động ${match.stadiumName}">
								</div>

								<h3 style="padding: 0 10px;">HAGL vs ${match.opponent}</h3>

								<p style="padding: 0 10px;">Ngày: ${match.matchDate} | Giờ:
									${match.matchTime}</p>

								<p style="padding: 0 10px;">Sân: ${match.stadiumName}</p>

								<p style="padding: 0 10px; font-weight: bold; color: green;">
									Giá từ:
									<fmt:formatNumber value="${match.minPrice}" type="currency"
										currencyCode="VND" />
								</p>
							</div>
						</a>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<p style="font-size: 1.2em; color: #555;">Hiện tại không có trận
					đấu nào sắp diễn ra. Vui lòng thử lại sau.</p>
				<p>Hoặc kiểm tra lại dữ liệu trong SQL Server Management Studio.</p>
			</c:otherwise>
		</c:choose>
	</main>

	<jsp:include page="footer.jsp" />
</body>
</html>