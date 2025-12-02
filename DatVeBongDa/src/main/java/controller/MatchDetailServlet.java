package controller;

import dao.MatchDAO;
import model.Match; // Sử dụng Match mới
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/matchDetail")
public class MatchDetailServlet extends HttpServlet {
    private MatchDAO matchDAO;

    public void init() {
        matchDAO = new MatchDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        String matchIdStr = request.getParameter("matchId");
        String area = request.getParameter("area"); // Khu vực được chọn (ví dụ: A1)

        try {
            int matchId = Integer.parseInt(matchIdStr);
            Match match = matchDAO.getMatchById(matchId);

            if (match == null) {
                response.sendRedirect("error.jsp?msg=Match not found.");
                return;
            }

            request.setAttribute("match", match);
            
            if (area != null && !area.isEmpty()) {
                double finalPrice = match.getBasePrice();
                
                // Mô phỏng logic giá: Phụ phí A1, Giảm giá C
                if (area.equals("A1")) finalPrice += 50000;
                if (area.equals("C")) finalPrice -= 50000;

                request.setAttribute("selectedArea", area);
                request.setAttribute("giaVeFinal", finalPrice);
                
                // Mô phỏng ghế đã đặt
                List<String> bookedSeats = new ArrayList<>();
                if (area.equals("A1")) bookedSeats.add("A1_5");
                request.setAttribute("bookedSeats", bookedSeats);
            }

            request.getRequestDispatcher("matchDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp?msg=Invalid match ID.");
        }
    }
}