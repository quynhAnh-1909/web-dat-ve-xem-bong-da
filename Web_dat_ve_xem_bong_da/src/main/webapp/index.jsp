<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Đặt Vé CAHN FC</title>
    <link rel="stylesheet" href="styles.css"/>
</head>
<body>
<header><h1>CAHN FC - Đặt Vé</h1></header>
<main>
    <c:forEach var="m" items="${matches}">
        <div class="match-card">
            <img src="${m.image}" class="match-banner"/>
            <div>
                <h2>${m.home} vs ${m.away}</h2>
                <p>${m.date} | ${m.time} | ${m.stadium}</p>
                <a href="BookController?id=${m.id}">Đặt Vé</a>
            </div>
        </div>
    </c:forEach>
</main>
<footer>©2025 CAHN FC</footer>
</body>
</html>
