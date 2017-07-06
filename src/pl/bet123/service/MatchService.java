package pl.bet123.service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;

import pl.bet123.dao.DAOFactory;
import pl.bet123.dao.MatchDAO;
import pl.bet123.dao.UserDAO;
import pl.bet123.model.Match;
import pl.bet123.model.User;
 
public class MatchService {
	
    public void addMatch(String teamA, String teamB, Date date) {
    	Match match = new Match();
    	match.setTeamA(teamA);
    	match.setTeamB(teamB);
    	match.setTimestamp(date);
        DAOFactory factory = DAOFactory.getDAOFactory();
        MatchDAO matchDao = factory.getMatchDAO();
        matchDao.create(match);
    }
     
    public List<Match> getAllMatch() {
        return getAllMatch(null);
    }
    
    public Match getMatchById(long matchId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MatchDAO matchDao = factory.getMatchDAO();
        Match match = matchDao.read(matchId);
        return match;
    }
     
    public List<Match> getAllMatch(Comparator<Match> comparator) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        MatchDAO matchDao = factory.getMatchDAO();
        List<Match> match = matchDao.getAll();
        if(comparator != null && match != null) {
            match.sort(comparator);
        }
        return match;
    }
}