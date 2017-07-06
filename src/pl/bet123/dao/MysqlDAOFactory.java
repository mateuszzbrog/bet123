package pl.bet123.dao;

public class MysqlDAOFactory extends DAOFactory {

	@Override
	public MatchDAO getMatchDAO() {
		return new MatchDAOImpl();
	}

	@Override
	public UserDAO getUserDAO() {
		return new UserDAOImpl();
	}

	@Override
	public BetDAO getBetDAO() {
		return new BetDAOImpl();
	}
	

}
