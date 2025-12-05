<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head><title>Đặt Vé</title></head>
<body>
<h2>Đặt vé cho trận: ${match.home} vs ${match.away}</h2>
<form action="BookController" method="post">
    <input type="hidden" name="matchId" value="${match.id}"/>
    <label>Loại vé:</label>
    <select name="type">
        <option value="VIP">VIP</option>
        <option value="Regular">Regular</option>
        <option value="Economy">Economy</option>
    </select>
    <label>Số lượng:</label>
    <input type="number" name="quantity" min="1" max="10" value="1"/>
    <label>Chọn ghế (ví dụ: A1,B1):</label>
    <input type="text" name="seats"/>
    <button type="submit">Xác nhận</button>
</form>
</body>
</html>
