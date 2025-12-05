package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Match;


@WebServlet("/BookController")
public class BookController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        List<Match> matches = new ArrayList<>();
        matches.add(new Match(1, "CAHN", "HAGL", "01/12/2025", "19:00", "Hàng Đẫy", "img/cahn-hagl.jpg"));
        matches.add(new Match(2, "CAHN", "HNFC", "15/12/2025", "19:00", "Hàng Đẫy", "img/cahn-hnfc.jpg"));
        getServletContext().setAttribute("MATCH_LIST", matches);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Match> matches = (List<Match>) getServletContext().getAttribute("MATCH_LIST");
        String id = request.getParameter("id");

        if (id == null) {
            request.setAttribute("matches", matches);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            Match match = matches.stream().filter(m -> m.getId() == Integer.parseInt(id)).findFirst().orElse(null);
            request.setAttribute("match", match);
            request.getRequestDispatcher("booking.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matchId = request.getParameter("matchId");
        String type = request.getParameter("type");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        List<String> selectedSeats = Arrays.asList(request.getParameter("seats").split(","));

        List<Match> matches = (List<Match>) getServletContext().getAttribute("MATCH_LIST");
        Match match = matches.stream().filter(m -> m.getId() == Integer.parseInt(matchId)).findFirst().orElse(null);

        int price = 200000;
        if("VIP".equals(type)) price = 500000;
        else if("Economy".equals(type)) price = 100000;

        int total = price * quantity;

        request.setAttribute("ticket", new model.Ticket(match,type,quantity,selectedSeats,total));
        request.getRequestDispatcher("success.jsp").forward(request, response);
    }
}
