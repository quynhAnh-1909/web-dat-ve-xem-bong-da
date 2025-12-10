<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header style="background-color: #00AA44; color: white; padding: 10px 20px; display: flex; justify-content: space-between; align-items: center;">
    
    <div class="logo" style="display: flex; align-items: center;">
        <img src="resources/hagl_logo.png" alt="HAGL Logo" style="height: 30px; margin-right: 15px;">
        
        <nav>
            <a href="matchList.jsp" style="color: white; margin-right: 20px; text-decoration: none;">Trang chủ</a>
            <a href="matchList" style="color: white; margin-right: 20px; text-decoration: none;">Lịch thi đấu</a>
            
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <a href="account" style="color: white; margin-right: 20px; text-decoration: none;">Tài khoản (${sessionScope.currentUser.fullName})</a>
                    <a href="logout" style="color: white; text-decoration: none;">Đăng xuất</a>
                </c:when>
                <c:otherwise>
                    <a href="login.jsp" style="color: white; text-decoration: none;">Tài khoản</a>
                </c:otherwise>
            </c:choose>
            
        </nav>
    </div>

    <div class="cart">
        <a href="#" style="color: white; text-decoration: none;">Giỏ hàng 🛒</a>
    </div>
</header>