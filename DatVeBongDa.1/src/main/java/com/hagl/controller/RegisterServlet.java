package com.hagl.controller;

import com.hagl.dao.UserDAO;
import com.hagl.model.User;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        // Hiển thị form Đăng ký
        request.getRequestDispatcher("register.jsp").forward(request, response); 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        // Lấy dữ liệu từ form Đăng ký (image_847e3d.png)
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User newUser = new User(0, fullName, email, password, phoneNumber);
        
        try {
            if (userDAO.registerUser(newUser)) {
                // Đăng ký thành công, chuyển hướng đến trang đăng nhập
                request.setAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Đăng ký thất bại (Email đã tồn tại)
                request.setAttribute("errorMessage", "Email này đã được sử dụng. Vui lòng thử lại.");
                request.setAttribute("newUser", newUser); // Giữ lại dữ liệu người dùng đã nhập
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi CSDL: Không thể hoàn tất đăng ký.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}