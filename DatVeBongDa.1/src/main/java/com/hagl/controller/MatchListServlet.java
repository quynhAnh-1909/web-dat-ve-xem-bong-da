package com.hagl.controller;

import com.hagl.dao.MatchDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/matchList")
public class MatchListServlet extends HttpServlet {
    private MatchDAO matchDAO;

    public void init() {
        // Khởi tạo MatchDAO (sẽ gọi BaseDAO để thiết lập Connection Pool)
        matchDAO = new MatchDAO(); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        try {
            // 1. Gọi DAO để lấy danh sách trận đấu (Không có logic xử lý trạng thái)
            request.setAttribute("matchList", matchDAO.getAllMatches());
            
            // 2. Forward tới JSP để hiển thị
            request.getRequestDispatcher("matchList.jsp").forward(request, response);
            
        } catch (SQLException e) {
            // Xử lý lỗi CSDL (Lỗi truy vấn SQL)
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi truy vấn CSDL: Không thể lấy danh sách trận đấu.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (RuntimeException e) {
             // Xử lý lỗi JNDI Lookup/Kết nối (từ BaseDAO)
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cấu hình CSDL: Không tìm thấy Connection Pool.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}