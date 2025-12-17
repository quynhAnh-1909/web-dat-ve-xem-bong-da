<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lịch thi đấu - HAGL Tickets</title>
<style>
    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4; margin: 0; }
    
    /* Bộ lọc tháng */
    .filter-section {
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 30px;
        text-align: center;
    }
    .filter-section select {
        padding: 10px 20px;
        font-size: 16px;
        border-radius: 5px;
        border: 1px solid #ddd;
        outline: none;
        cursor: pointer;
    }

    /* Danh sách trận đấu */
    .schedule-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 25px;
    }
    .match-item {
        background: white;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        transition: transform 0.3s ease;
    }
    .match-item:hover { transform: translateY(-5px); }
    
    .img-box {
        height: 180px;
        background: #f9f9f9;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 15px;
    }
    .img-box img { max-width: 100%; max-height: 100%; object-fit: contain; }

    .info-box { padding: 20px; }
    .info-box h3 { margin: 0 0 10px 0; color: #004d31; }
    .info-box p { margin: 5px 0; color: #555; font-size: 14px; }
    
    .status-tag {
        display: inline-block;
        padding: 4px 12px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: bold;
        background: #e8f5e9;
        color: #2e7d32;
        margin-bottom: 10px;
    }
    
    .btn-booking {
        display: block;
        text-align: center;
        background: #004d31;
        color: white;
        text-decoration: none;
        padding: 12px;
        border-radius: 6px;
        margin-top: 15px;
        font-weight: bold;
    }
    .no-data { text-align: center; padding: 50px; color: #888; }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main style="max-width: 1200px; margin: 40px auto; padding: 0 20px;">
        <h1 style="color: #004d31; border-left: 5px solid #004d31; padding-left: 15px; margin-bottom: 30px;">
            Lịch Thi Đấu HAGL
        </h1>

        <div class="filter-section">
            <form action="schedule" method="get" id="filterForm">
                <label style="font-weight: bold; margin-right: 10px;">Lọc theo tháng năm 2025:</label>
                <select name="month" onchange="document.getElementById('filterForm').submit()">
                    <c:forEach var="m" begin="1" end="12">
                        <option value="${m}" ${currentMonth == m ? 'selected' : ''}>Tháng ${m}</option>
                    </c:forEach>
                </select>
            </form>
        </div>

        <c:choose>
            <c:when test="${not empty matchList}">
                <div class="schedule-grid">
                    <c:forEach var="match" items="${matchList}">
                        <div class="match-item">
                            <div class="img-box">
                                <img src="${root}/resources/${match.opponentImageName}" alt="Logo ${match.opponent}">
                            </div>
                            <div class="info-box">
                                <span class="status-tag">V-League 2025/26</span>
                              <h3 style="margin: 0 0 10px 0;">HAGL vs ${match.opponent}</h3>
                            <p style="margin: 5px 0;">📅 Ngày: ${match.matchDate}</p>
                            <p style="margin: 5px 0;">⏰ Giờ: ${match.matchTime}</p>
                            <p style="margin: 5px 0;">🏟️ Sân: ${match.stadiumName}</p>
                           
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="no-data">
                    <img src="${root}/resources/hagl_logo.png" style="width: 80px; opacity: 0.3; margin-bottom: 20px;">
                    <p>Rất tiếc, hiện tại chưa có lịch thi đấu chính thức trong tháng ${currentMonth}.</p>
                    <a href="schedule?month=${currentMonth + 1}" style="color: #004d31;">Xem tháng tiếp theo →</a>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>