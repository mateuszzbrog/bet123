package pl.bet123.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.bet123.model.Match;
import pl.bet123.model.User;
import pl.bet123.service.BetService;
import pl.bet123.service.MatchService;


@WebServlet("/bet")
public class BetController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		if(request.getUserPrincipal() != null) {
			saveMatchesInRequest(request);
            request.getRequestDispatcher("bet.jsp").forward(request, response);
            
        } else {
            response.sendError(403);
        }
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int goalsA = 0;
        int goalsB = 0;
        String goalsAString = request.getParameter("inputGoalA");
        String goalsBString = request.getParameter("inputGoalB");
        if(goalsAString != null && !"".equals(goalsAString)) {
        	goalsA = Integer.parseInt(goalsAString);
        }
        if(goalsBString != null && !"".equals(goalsBString)) {
        	goalsB = Integer.parseInt(goalsBString);
        }
        long matchId = Long.parseLong(request.getParameter("inputMatchId"));
        User authenticatedUser = (User) request.getSession().getAttribute("user");
        if(request.getUserPrincipal() != null) {
            BetService betService = new BetService();
            betService.addBet(matchId, authenticatedUser.getId(), goalsA, goalsB);
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            response.sendError(403);
        }
    }
	
	private void saveMatchesInRequest(HttpServletRequest request) {
        MatchService matchService = new MatchService();
        List<Match> allMatch = matchService.getAllMatch();
        request.setAttribute("matches", allMatch);
    }
}