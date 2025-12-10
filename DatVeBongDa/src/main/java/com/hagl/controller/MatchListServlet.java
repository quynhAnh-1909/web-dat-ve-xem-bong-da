package com.hagl.controller;

import com.hagl.dao.MatchDAO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

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
            // 1. Gọi DAO để lấy danh sách trận đấu sắp diễn ra
            request.setAttribute("matchList", matchDAO.getAllMatches());
            
            // 2. Forward tới JSP để hiển thị
            request.getRequestDispatcher("matchList.jsp").forward(request, response);
            
        } catch (SQLException e) {
            // 3. Xử lý lỗi CSDL (ví dụ: lỗi kết nối Pool, lỗi truy vấn)
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi truy vấn CSDL: Không thể lấy danh sách trận đấu.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (RuntimeException e) {
             // Xử lý lỗi JNDI Lookup (từ BaseDAO)
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cấu hình CSDL: Không tìm thấy Connection Pool.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}