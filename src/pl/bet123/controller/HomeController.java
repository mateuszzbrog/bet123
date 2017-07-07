package pl.bet123.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.bet123.model.Bet;
import pl.bet123.model.Match;
import pl.bet123.model.User;
import pl.bet123.service.*;

@WebServlet("")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveUsersInRequest(request);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
 
	private void saveUsersInRequest(HttpServletRequest request) {
		User user;
        UserService userService = new UserService();
        MatchService matchService = new MatchService();
        BetService betService = new BetService();
        List<User> allUsers = userService.getAllUsers();
        List<Match> allMatches = matchService.getAllMatch();
        Match match, finalMatch = null;
        long howManyBet =  (int) betService.getLastBet().getId();
        for(int i=0; i<=howManyBet; i++){
        	//bierzemy ka¿dy bet(typ bukmacherski) po kolei
        	Bet bet = betService.getBetById(i);
        	if(bet != null){
        	long idUser = bet.getUserId();
        	user = userService.getUserById(idUser);
        	long idMatch = bet.getMatchId();
        	//porównujemy z ka¿dym meczem
        	for(int j=0; j<allMatches.size();j++){
        		match = allMatches.get(j);
        		if(match.getId() == idMatch){
        			finalMatch = allMatches.get(j);
        		}
        		//przypisanie punktów do beta(typu bukmacherskiego)
        		if(finalMatch != null){
        			if((finalMatch.getGoalsA() == bet.getGoalsA()) && (finalMatch.getGoalsB() == bet.getGoalsB())) {
        				bet.setPoints(3);
        			} else if((finalMatch.getGoalsA() > finalMatch.getGoalsB()) && (bet.getGoalsA() > bet.getGoalsB())) {
        				bet.setPoints(1);
        			} else if((finalMatch.getGoalsA() < finalMatch.getGoalsB()) && (bet.getGoalsA() < bet.getGoalsB())) {
        				bet.setPoints(1);
        			} else if((finalMatch.getGoalsA() == finalMatch.getGoalsB()) && (bet.getGoalsA() == bet.getGoalsB())) {
        				bet.setPoints(1);
        			} else bet.setPoints(0);
        		} 
        		betService.updatePoints(bet);
        	}
        	//aktualizujemy wynik ka¿dego u¿ytkownika
        	int finalResult = 0;
        	List<Bet> betsByUser = betService.getAllBetsByUserId(idUser);
        	for(int k=0;k<betsByUser.size();k++){
        		finalResult = finalResult + betsByUser.get(k).getPoints();
        	}
        	user.setResult(finalResult);
        	userService.updateUser(user);
        	}
        }
        allUsers = userService.getAllUsers();
        allUsers = userService.getAllUsers(new Comparator<User>() {
            //more votes = higher
            @Override
            public int compare(User d1, User d2) {
                int d1Result = d1.getResult();
                int d2Result = d2.getResult();
                if(d1Result < d2Result) {
                    return 1;
                } else if(d1Result > d2Result) {
                    return -1;
                }
                return 0;
            }
        });
        for(int i=0;i<allUsers.size();i++){
        	allUsers.get(i).setId(i+1);
        }
        
        request.setAttribute("users", allUsers);
        
    }
	
}
