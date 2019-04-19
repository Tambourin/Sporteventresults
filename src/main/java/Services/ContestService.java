
package Services;

import dao.ContestDao;
import dao.ContestDaoJdbc;
import domain.Contest;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class for operations related to Contest entity
 * @author Olavi
 */
public class ContestService {
    
    ContestDao contestDao = new ContestDaoJdbc();
    
    /**
     * Delete a contest from database
     * @param contest Contest to be deleted
     * @return Returns true if operation was success.
     */
    public boolean delete(Contest contest) {
        if (contest.getParticipantsNumber() > 0) {            
            return false;
        }
        contestDao.delete(contest.getId());
        return true;
    }
    
    /**
     * Deletes a contest from database
     * @param contest The contest to be deleted. 
     */
    public void update(Contest contest) {
        if (contest == null) {
            System.out.println("Contest null");
            return;
        }
        contestDao.update(contest);
    }
    
    /**
     * Adds a new contest to database according to data given as parameters
     * @param name Name of the contest that is created
     * @param startingTime Starting time of the contest
     */
    public void addNew(String name, String startingTime) {
        Contest newContest = new Contest(name, LocalTime.parse(startingTime));
        contestDao.create(newContest);
    }
 
    /**
     * Returns all contests in database
     * @return Returns a list of contests
     */
    public List<Contest> findAll() {
        return contestDao.findAll();
    }
}
