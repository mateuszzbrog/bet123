package pl.bet123.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import pl.bet123.model.Bet;
import pl.bet123.model.User;
import pl.bet123.util.ConnectionProvider;
 
public class UserDAOImpl implements UserDAO {
	
	private static final String CREATE_USER = 
		    "INSERT INTO user(username, email, password, is_active, result) VALUES(:username, :email, :password, :active, :result);";
	private static final String READ_USER = 
		      "SELECT user_id, username, email, password, is_active, result FROM user WHERE user_id = :id";
	private static final String READ_USER_BY_USERNAME = 
		      "SELECT user_id, username, email, password, is_active, result FROM user WHERE username = :username";
	private static final String READ_ALL_USERS = "SELECT user_id, username, email, password, is_active, result FROM user ; ";
    private static final String UPDATE_RESULT = "UPDATE user SET result=:result WHERE user_id=:user_id;";

	
	private NamedParameterJdbcTemplate template;
	
	public UserDAOImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }
	
    @Override
    public User create(User user) {
    	 User resultUser = new User(user);
         KeyHolder holder = new GeneratedKeyHolder();
         SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
         int update = template.update(CREATE_USER, paramSource, holder);
         if(update > 0) {
             resultUser.setId((Long)holder.getKey());
             setPrivigiles(resultUser);
         }
         return resultUser;
    }
    
    private void setPrivigiles(User user) {
        final String userRoleQuery = "INSERT INTO user_role(username) VALUES(:username)";
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        template.update(userRoleQuery, paramSource);
    }
 
    @Override
    public User read(Long primaryKey) {
    	User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("id", primaryKey);
        resultUser = template.queryForObject(READ_USER, paramSource, new UserRowMapper());
        return resultUser;
    }
 
    @Override
    public boolean update(User user) {
		boolean result = false;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("result", user.getResult());
        paramMap.put("user_id", user.getId());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_RESULT, paramSource);
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
    public List<User> getAll() {
    	List<User> users = template.query(READ_ALL_USERS, new UserRowMapper());
        return users;
    }
 
    @Override
    public User getUserByUsername(String username) {
    	User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("username", username);
        resultUser = template.queryForObject(READ_USER_BY_USERNAME, paramSource, new UserRowMapper());
        return resultUser;
    }
    
    private class UserRowMapper implements RowMapper<User> {
    	 
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setResult(resultSet.getInt("result"));
            return user;
        }
         
    }
}