package pl.bet123.service;

import java.util.Comparator;
import java.util.List;

import pl.bet123.dao.BetDAO;
import pl.bet123.dao.DAOFactory;
import pl.bet123.dao.UserDAO;
import pl.bet123.model.Bet;
import pl.bet123.model.User;

 
public class UserService {
	
    public void addUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setActive(true);
        user.setResult(0);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        userDao.create(user);
    }
    
    public User getUserById(long userId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        User user = userDao.read(userId);
        return user;
    }
     
    public User getUserByUsername(String username) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        User user = userDao.getUserByUsername(username);
        return user;
    }
    
    public User updateUser(User user) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        User userToUpdate = userDao.getUserByUsername(user.getUsername());
        userToUpdate.setResult(user.getResult());
        userDao.update(userToUpdate);
        return userToUpdate;
    }
    
    public List<User> getAllUsers() {
        return getAllUsers(null);
    }
    
    public List<User> getAllUsers(Comparator<User> comparator) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        List<User> users = userDao.getAll();
        if(comparator != null && users != null) {
            users.sort(comparator);
        }
        return users;
    }
}