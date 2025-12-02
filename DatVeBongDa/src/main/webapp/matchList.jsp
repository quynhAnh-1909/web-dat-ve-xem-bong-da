<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Match Schedule HAGL</title></head>
<body>
    <h1>HAGL Match Schedule ⚽️</h1>
    
    <c:forEach var="match" items="${matchList}">
        <div style="border: 1px solid #ccc; margin-bottom: 10px; padding: 10px;">
            <h2>HAGL vs ${match.opponent}</h2>
            <p>Date: ${match.matchDate} - Time: ${match.matchTime}</p>
            <p>Base Price: ${match.basePrice} VND</p>
            <a href="matchDetail?matchId=${match.matchId}">View Details & Book</a>
        </div>
    </c:forEach>
</body>
</html>