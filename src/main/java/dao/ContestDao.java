
package dao;

import domain.Contest;
import java.util.List;

/**
 *
 * @author Olavi
 */
public interface ContestDao {

   /**
     * Creates a record to database accordind to param
     * @param contest

     */
    void create(Contest contest);    

    /**
     * Updates a database record based on param
     * @param contest
     */
    void update(Contest contest);

    /**
     * Deletes a database record
     * @param key An id of record to be deleted
     */
    void delete(Integer key);

    /**
     * Seeks for a contest according to it's id
     * @param key
     * @return Returns the found contest. If no contest if found, returns null
     */
    Contest findById(Integer key);

    /**
     * Finds all records in Contest table
     * @return Returns list of all contests in database
     */
    List<Contest> findAll();
    
}

