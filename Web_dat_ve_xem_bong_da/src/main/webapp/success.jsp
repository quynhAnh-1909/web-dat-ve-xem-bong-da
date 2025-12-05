<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head><title>Đặt vé thành công</title></head>
<body>
<h2>Đặt vé thành công!</h2>
<p>Trận: ${ticket.match.home} vs ${ticket.match.away}</p>
<p>Loại vé: ${ticket.type}</p>
<p>Số lượng: ${ticket.quantity}</p>
<p>Ghế: <c:forEach var="s" items="${ticket.seats}">${s} </c:forEach></p>
<p>Tổng tiền: ${ticket.totalPrice} VND</p>
<a href="BookController">Trở về trang chủ</a>
</body>
</html>
