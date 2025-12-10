package com.hagl.controller;

import com.hagl.dao.UserDAO;
import com.hagl.model.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO(); // Khởi tạo UserDAO
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        // Hiển thị form Đăng nhập
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            // 1. Gọi DAO để kiểm tra thông tin đăng nhập
            User user = userDAO.checkLogin(email, password);

            if (user != null) {
                // 2. Đăng nhập thành công: Tạo Session
                HttpSession session = request.getSession();
                session.setAttribute("currentUser", user); 
                
                // 3. Chuyển hướng đến trang chủ (danh sách trận đấu)
                response.sendRedirect("matchList"); 
            } else {
                // 4. Đăng nhập thất bại
                request.setAttribute("errorMessage", "Email hoặc mật khẩu không đúng.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
             // 5. Xử lý lỗi CSDL
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi CSDL khi đăng nhập.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}