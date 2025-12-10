package com.hagl.controller;

import com.hagl.dao.MatchDAO;
import com.hagl.dao.StandDAO;
import com.hagl.model.Match;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/matchDetail")
public class MatchDetailServlet extends HttpServlet {
    private MatchDAO matchDAO;
    private StandDAO standDAO;

    public void init() {
        matchDAO = new MatchDAO();
        standDAO = new StandDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        String matchIdStr = request.getParameter("matchId");
        String selectedStand = request.getParameter("stand"); // Khu vực được chọn (A1, B, C...)
        
        if (matchIdStr == null) {
            response.sendRedirect("matchList");
            return;
        }

        try {
            int matchId = Integer.parseInt(matchIdStr);
            Match match = matchDAO.getMatchById(matchId);
            
            if (match == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy trận đấu.");
                return;
            }
            request.setAttribute("match", match);

            if (selectedStand == null || selectedStand.isEmpty()) {
                // GIAI ĐOẠN 1: Hiển thị chi tiết và sơ đồ để chọn khu vực
                request.getRequestDispatcher("matchDetail.jsp").forward(request, response);
                return;
            } 
            
            // GIAI ĐOẠN 2: Hiển thị lưới ghế và giá
            request.setAttribute("selectedStand", selectedStand);
            
            // 1. Lấy giá cố định của khu vực (từ KHUVUC)
            double finalPrice = standDAO.getFinalPrice(selectedStand);
            request.setAttribute("finalPrice", finalPrice);
            
            // 2. Lấy danh sách ghế đã đặt thành công
            List<String> bookedSeats = standDAO.getBookedSeats(matchId, selectedStand);
            request.setAttribute("bookedSeats", bookedSeats);
            
            // Chuyển sang JSP hiển thị lưới ghế và form đặt vé
            request.getRequestDispatcher("seatSelection.jsp").forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi dữ liệu hoặc CSDL khi truy vấn thông tin trận đấu/khán đài.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}