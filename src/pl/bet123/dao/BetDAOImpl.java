package pl.bet123.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import pl.bet123.model.Bet;
import pl.bet123.model.Match;
import pl.bet123.util.ConnectionProvider;
 
public class BetDAOImpl implements BetDAO {
	
	private static final String CREATE_BET = "INSERT INTO bet(match_id, user_id, goals_a, goals_b, date, points) "
            + "VALUES (:match_id, :user_id, :goals_a, :goals_b, :date, :points);";
    private static final String READ_BET = "SELECT bet_id, match_id, user_id, goals_a, goals_b, date, points "
            + "FROM bet WHERE bet_id = :bet_id;";
    private static final String READ_BET_BY_MATCH_USE_IDS = "SELECT bet_id, match_id, user_id, goals_a, goals_b, date, points "
            + "FROM bet WHERE user_id = :user_id AND match_id = :match_id;";
    private static final String UPDATE_BET = "UPDATE bet SET date=:date, goals_a=:goals_a, goals_b=:goals_b WHERE bet_id=:bet_id;";
    
    private static final String READ_ALL_BET_BY_USER_ID = "SELECT bet_id, match_id, user_id, goals_a, goals_b, date, points "
            + "FROM bet WHERE user_id = :user_id ;";
    private static final String UPDATE_POINTS = "UPDATE bet SET points=:points WHERE bet_id=:bet_id;";
    
    
    private NamedParameterJdbcTemplate template;
    
    public BetDAOImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
    
	@Override
	public Bet create(Bet bet) {
		Bet betCopy = new Bet(bet);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("match_id", betCopy.getMatchId());
        paramMap.put("user_id", betCopy.getUserId());
        paramMap.put("date", betCopy.getDate());
        paramMap.put("goals_a", betCopy.getGoalsA());
        paramMap.put("goals_b", betCopy.getGoalsB());
        paramMap.put("points", betCopy.getPoints());
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(CREATE_BET, paramSource, holder);
        if(update > 0) {
            betCopy.setId((Long)holder.getKey());
        }
        return betCopy;
	}

	@Override
	public Bet read(Long primaryKey) {
		SqlParameterSource paramSource = new MapSqlParameterSource("bet_id", primaryKey);
        Bet bet = null;
        try {
        	bet = template.queryForObject(READ_BET, paramSource, new BetRowMapper());
        } catch(EmptyResultDataAccessException e) {
            //vote not found
        }
        return bet;
	}

	@Override
	public boolean updatePoints(Bet bet) {
		boolean result = false;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("points", bet.getPoints());
        paramMap.put("bet_id", bet.getId());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_POINTS, paramSource);
        if(update > 0) {
            result = true;
        }
        return result;
	}
	
	public boolean update(Bet bet) {
		boolean result = false;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("date", bet.getDate());
        paramMap.put("goals_a", bet.getGoalsA());
        paramMap.put("goals_b", bet.getGoalsB());
        paramMap.put("bet_id", bet.getId());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_BET, paramSource);
        if(update > 0) {
            result = true;
        }
        return result;
	}

	@Override
	public boolean delete(Long key) {
		return false;
	}

	@Override
	public List<Bet> getAll() {
		return null;
	}

	@Override
	public Bet getBetByUserIdMatchId(long userId, long matchId) {
		Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", userId);
        paramMap.put("match_id", matchId);
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        Bet bet = null;
        try {
        	bet = template.queryForObject(READ_BET_BY_MATCH_USE_IDS, paramSource, new BetRowMapper());
        } catch(EmptyResultDataAccessException e) {
            //vote not found
        }
        return bet;
	}
	
	public List<Bet> getAllBetByUserId(long userId) {
		Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("user_id", userId);
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
    	List<Bet> bets = null;
    	try {
        	bets = template.query(READ_ALL_BET_BY_USER_ID, paramSource, new BetRowMapper());
        } catch(EmptyResultDataAccessException e) {
            //vote not found
        }
        return bets;
    }
	
	private class BetRowMapper implements RowMapper<Bet> {
        @Override
        public Bet mapRow(ResultSet resultSet, int row) throws SQLException {
            Bet bet = new Bet();
            bet.setId(resultSet.getLong("bet_id"));
            bet.setUserId(resultSet.getLong("user_id"));
            bet.setMatchId(resultSet.getLong("match_id"));
            bet.setDate(resultSet.getTimestamp("date"));
            bet.setGoalsA(resultSet.getInt("goals_a"));
            bet.setGoalsB(resultSet.getInt("goals_b"));
            bet.setPoints(resultSet.getInt("points"));
            return bet;
        }
         
    }
}