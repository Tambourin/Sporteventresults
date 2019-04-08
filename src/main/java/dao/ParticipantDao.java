
package dao;

import domain.Contest;
import domain.Participant;
import java.util.*;

/**
 *
 * @author Olavi
 */
public interface ParticipantDao {
    
    /**
     * Inserts a record into the Participant table using values of
     * paticipant object that is passed to the method.
     * @param participant Participant whose values are inserted into table.
     */
    void create(Participant participant);    

    /**
     * Updates a rocord in database
     * @param participant participant instance whose data is updated
     */
    void update(Participant participant);

    /**
     * Deletes a record with given id from the Participant table 
     * @param key ID key for a record to be deleted.
     */
    void delete(Integer key);

    /**
     * Uses key parameter to find a certain participant from Participant table.
     * @param key
     * @return 
     */
    Participant findById(Integer key);

    /**
     * Seeks for a record in database where name field is like parameter
     * @param name
     * @return Returns a list of participants whose name is like param
     */
    List<Participant> findByNameLike(String name);
    
    /**
     * Returns a participant whose bidnumber matches param
     * @param bidNumber
     * @return 
     */
    Participant findByBidNumber(Integer bidNumber);

    /**
     * Finds participants whose contest equals param
     * @param contest
     * @return Returns a list of participants whose contest equals param
     */
    List<Participant> listByContest(Contest contest);

    /**
     * Return a list of all participants in the database
     * @return List of all participants
     */
    List<Participant> listAll();
     /**
     * Finds participants whose contest equals param
     * @param contest
     * @return Returns a list of participants whose contest equals param
     */
    Integer countByContest(Contest contest);
    

}

