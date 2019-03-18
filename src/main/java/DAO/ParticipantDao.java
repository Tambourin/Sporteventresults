
package DAO;

import domain.Contest;
import domain.Participant;
import java.sql.*;
import java.util.*;

public interface ParticipantDao {
    void create(Participant participant) ;    
    void update(Participant participant) ;
    void delete(Integer key) ;
    Participant findById(Integer key) ;
    Participant findByName(String name);
    List<Participant> listByContest(Contest contest);
    List<Participant> listAll() ;
}

