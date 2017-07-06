package pl.bet123.dao;

import java.util.List;

import pl.bet123.model.Match;
 
public interface MatchDAO extends GenericDAO<Match, Long>{
 
    List<Match> getAll();
     
}