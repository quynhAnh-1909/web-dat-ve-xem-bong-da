package com.hagl.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa tồn tại
        if (session != null) {
            session.invalidate(); // Hủy session và xóa tất cả attribute (bao gồm currentUser)
        }
        
        // Chuyển hướng về trang chủ hoặc trang đăng nhập
        response.sendRedirect("matchList"); 
    }
}