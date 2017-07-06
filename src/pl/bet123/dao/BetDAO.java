package pl.bet123.dao;

import java.util.List;

import pl.bet123.model.Bet;

public interface BetDAO extends GenericDAO<Bet, Long> {
 
    public Bet getBetByUserIdMatchId(long userId, long matchId);

	public List<Bet> getAllBetByUserId(long userId);

	public boolean updatePoints(Bet bet);
}