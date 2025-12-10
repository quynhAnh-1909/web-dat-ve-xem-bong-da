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

}