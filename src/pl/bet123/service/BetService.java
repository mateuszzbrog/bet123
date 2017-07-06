package pl.bet123.service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import pl.bet123.dao.BetDAO;
import pl.bet123.dao.BetDAOImpl;
import pl.bet123.dao.DAOFactory;
import pl.bet123.dao.MatchDAO;
import pl.bet123.model.Bet;
import pl.bet123.model.Match;
 
public class BetService {
    public Bet addBet(long matchId, long userId, int goalsA, int goalsB) {
    	Bet bet = new Bet();
    	bet.setMatchId(matchId);
    	bet.setUserId(userId);
    	bet.setDate(new Timestamp(new Date().getTime()));
    	bet.setGoalsA(goalsA);
    	bet.setGoalsB(goalsB);
        DAOFactory factory = DAOFactory.getDAOFactory();
        BetDAO betDao = factory.getBetDAO();
        bet = betDao.create(bet);
        return bet;
    }
     
    public Bet updateBet(long matchId, long userId, int goalsA, int goalsB) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BetDAO betDao = factory.getBetDAO();
        Bet betToUpdate = betDao.getBetByUserIdMatchId(userId, matchId);
        if(betToUpdate != null) {
            betToUpdate.setGoalsA(goalsA);
            betToUpdate.setGoalsB(goalsB);
            betDao.update(betToUpdate);
        }
        return betToUpdate;
    }
    
    public Bet updatePoints(Bet bet) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BetDAO betDao = factory.getBetDAO();
        Bet betToUpdate = betDao.read(bet.getId());
        if(betToUpdate != null) {
            betToUpdate.setPoints(bet.getPoints());
            betDao.updatePoints(betToUpdate);
        }
        return betToUpdate;
    }
     
    public Bet addOrUpdateBet(long matchId, long userId, int goalsA, int goalsB) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BetDAO betDao = factory.getBetDAO();
        Bet bet = betDao.getBetByUserIdMatchId(userId, matchId);
        Bet resultBet = null;
        if(bet == null) {
            resultBet = addBet(matchId, userId, goalsA, goalsB);
        } else {
            resultBet = updateBet(matchId, userId, goalsA, goalsB);
        }
        return resultBet;
    }
     
    public Bet getBetByMatchUserId(long matchId, long userId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BetDAO betDao = factory.getBetDAO();
        Bet bet = betDao.getBetByUserIdMatchId(userId, matchId);
        return bet;
    }
    
    public Bet getBetById(long betId){
    	DAOFactory factory = DAOFactory.getDAOFactory();
    	BetDAO betDao = factory.getBetDAO();
    	Bet bet = betDao.read(betId);
    	return bet;
    }
    public List<Bet> getAllBetsByUserId(long userId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        BetDAO betDao = factory.getBetDAO();
        List<Bet> bets = betDao.getAllBetByUserId(userId);
        return bets;
    }
}