<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập Tài khoản</title>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <main style="max-width: 400px; margin: 40px auto; padding: 20px; border: 1px solid #ddd; text-align: center; background-color: #f7f7f7;">
        
        <img src="resources/hagl_logo.png" alt="HAGL Logo" style="height: 60px; margin-bottom: 20px;">
        <h2>ĐĂNG NHẬP</h2>
        
        <c:if test="${not empty errorMessage}">
            <p style="color: red; font-weight: bold;">${errorMessage}</p>
        </c:if>
        
        <c:if test="${not empty successMessage}">
            <p style="color: green; font-weight: bold;">${successMessage}</p>
        </c:if>
        
        <form action="login" method="POST">
            <input type="email" name="email" placeholder="Email *" required 
                   style="width: 90%; padding: 12px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px;">
                   
            <input type="password" name="password" placeholder="Mật khẩu *" required 
                   style="width: 90%; padding: 12px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px;">
            
            <button type="submit" 
                    style="width: 90%; padding: 12px; background-color: #00AA44; color: white; border: none; cursor: pointer; margin-top: 20px; border-radius: 4px;">
                Đăng nhập
            </button>
            
            <p style="margin-top: 15px;">
                Bạn chưa có tài khoản? 
                <a href="register.jsp" style="color: #00AA44; text-decoration: none; font-weight: bold;">Đăng ký</a>
            </p>
        </form>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>