package controller;


import dao.MatchDAO; // Sử dụng MatchDAO mới
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/matchList")
public class MatchListServlet extends HttpServlet {
    private MatchDAO matchDAO;

    public void init() {
        matchDAO = new MatchDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
           throws ServletException, IOException {
        
        // 1. Gọi Model (DAO)
        request.setAttribute("matchList", matchDAO.getAllMatches());
        
        // 2. Điều hướng đến View (JSP)
        request.getRequestDispatcher("matchList.jsp").forward(request, response);
    }
}