package pl.bet123.dao;

import java.util.List;

import pl.bet123.model.User;
 
 
public interface UserDAO extends GenericDAO<User, Long> {
 
    List<User> getAll();
    User getUserByUsername(String username);
     
}