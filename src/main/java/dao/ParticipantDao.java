
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
     *
     * @param participant
     */
    void create(Participant participant);    

    /**
     *
     * @param participant
     */
    void update(Participant participant);

    /**
     *
     * @param key
     */
    void delete(Integer key);

    /**
     *
     * @param key
     * @return
     */
    Participant findById(Integer key);

    /**
     *
     * @param name
     * @return
     */
    List<Participant> findByNameLike(String name);
    
    Participant findByBidNumber(Integer bidNumber);

    /**
     *
     * @param contest
     * @return
     */
    List<Participant> listByContest(Contest contest);

    /**
     *
     * @return
     */
    List<Participant> listAll();
    
    Integer countByContest(Contest contest);
    

}

