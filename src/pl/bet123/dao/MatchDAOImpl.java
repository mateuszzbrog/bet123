package pl.bet123.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

 
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import pl.bet123.model.Match;
import pl.bet123.model.User;
import pl.bet123.util.ConnectionProvider;
 
public class MatchDAOImpl implements MatchDAO {
	
	private static final String CREATE_MATCH = "INSERT INTO game(team_a, team_b, date, goals_a, goals_b) VALUES(:team_a, :team_b, :date, :goals_a, :goals_b) ; ";
	private static final String READ_ALL_MATCH = 
		      "SELECT match_id, date, team_a, team_b, goals_a, goals_b FROM game ; ";
	private static final String READ_MATCH = 
		        "SELECT match_id, date, team_a, goals_a, goals_b, team_b FROM game WHERE match_id=:match_id;";
	private static final String UPDATE_MATCH = "";
	
	private NamedParameterJdbcTemplate template;
	
	public MatchDAOImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
 
    @Override
    public Match create(Match match) {
    	Match resultMatch = new Match(match);
         KeyHolder holder = new GeneratedKeyHolder();
         SqlParameterSource paramSource = new BeanPropertySqlParameterSource(match);
         int update = template.update(CREATE_MATCH, paramSource, holder);
         if(update > 0) {
             resultMatch.setId((Long)holder.getKey());
         }
         return resultMatch;
    }
    
 
    @Override
    public Match read(Long primaryKey) {
    	SqlParameterSource paramSource = new MapSqlParameterSource("match_id", primaryKey);
        Match match = template.queryForObject(READ_MATCH, paramSource, new MatchRowMapper());
        return match;
    }
 
    @Override
    public boolean update(Match updateObject) {
        return false;
    }
 
    @Override
    public boolean delete(Long key) {
        return false;
    }
 
    @Override
    public List<Match> getAll() {
    	List<Match> matches = template.query(READ_ALL_MATCH, new MatchRowMapper());
        return matches;
    }
    
    private class MatchRowMapper implements RowMapper<Match> {
        @Override
        public Match mapRow(ResultSet resultSet, int row) throws SQLException {
        	Match match = new Match();
        	match.setId(resultSet.getLong("match_id"));
        	match.setTeamA(resultSet.getString("team_a"));
        	match.setTeamB(resultSet.getString("team_b"));
        	match.setGoalsA(resultSet.getInt("goals_a"));
        	match.setGoalsB(resultSet.getInt("goals_b"));
        	match.setTimestamp(resultSet.getDate("date"));
            return match;
        }
    }
 
}