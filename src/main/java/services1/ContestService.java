
package services1;

import dao.ContestDao;
import dao.ContestDaoJdbc;
import domain.Contest;
import domain.Event;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class for operations related to Contest entity
 * @author Olavi
 */
public class ContestService {
    
    ContestDao contestDao = new ContestDaoJdbc();
    
    /**
     * Delete a contest from database. Can not be deleted if there are participants
     * in contest.
     * @param contest Contest to be deleted
     * @return Returns true if operation was success.
     */
    public boolean delete(Contest contest) {
        if (contest == null) {
            return false;
        }
        if (contest.getParticipantsNumber() > 0) {            
            return false;
        }
        contestDao.delete(contest.getId());
        return true;
    }
    
    /**
     * Deletes a contest from database
     * @param contest The contest to be deleted. 
     * @return Returns false if contest was null
     */
    public boolean update(Contest contest) {
        if (contest == null) {
            System.out.println("Contest null");
            return false;
        }
        contestDao.update(contest);
        return true;
    }
    
    /**
     * Adds a new contest to database according to data given as parameters
     * @param name Name of the contest that is created
     * @param startingTime Starting time of the contest
     * @return Returns id of created contest
     */
    public Integer addNew(String name, String startingTime, Event event) {
        LocalTime time;
        try {
            time = LocalTime.parse(startingTime);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        Contest newContest = new Contest(name, time, event);
        return contestDao.create(newContest);
    }
 
    public List<Contest> findAllByEvent(Event event) {
        return contestDao.findAllByEvent(event);
    }
    
    /**
     * Returns all contests in database
     * @return Returns a list of contests
     */
    public List<Contest> findAll() {
        return contestDao.findAll();
    }
}
