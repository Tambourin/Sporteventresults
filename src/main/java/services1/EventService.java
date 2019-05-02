
package services1;

import dao.ContestDaoJdbc;
import dao.EventDaoJdbc;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Event;
import domain.Participant;
import java.util.List;

/**
 * Service class for operations related to Event entity
 * @author Olavi
 */
public class EventService {
    EventDaoJdbc eventDao = new EventDaoJdbc();
    ContestDaoJdbc contestDao = new ContestDaoJdbc();
    ParticipantDaoJdbc participantDao = new ParticipantDaoJdbc();
    
    /**
     * Seeks and returns a Event based on id-key passed as parameter
     * @param id Event's id in database
     * @return Returns event
     */
    public Event findById(Integer id) {
        return eventDao.findById(id);
    }
    
    /**
     * Returns List of all events in database.
     * @return Returns List of all events in database
     */
    public List<Event> findAll() {
        return eventDao.findAll();
    }
    /**
     * Updates event's data in database
     * @param event Event to be updated
     */
    public void update(Event event) {
        eventDao.update(event);
    }
    
    /**     * 
     * @param event Event model to create to database
     * @return Return id attribute of created record;
     */
    public Integer create(Event event) {
        return eventDao.create(event);
    }
    
    /**
     * Delete event from database and delete all contests and participants
     * that are associated with it.
     * @param event 
     */
    public void deleteEventAndAssociatedContests(Event event) {
        List<Contest> contestsInEvent = contestDao.findAllByEvent(event);
        for (Contest c : contestsInEvent) {
            List<Participant> participants = participantDao.listByContest(c);
            for (Participant p : participants) {
                participantDao.delete(p.getId());
            }
            contestDao.delete(c.getId());
        }
        
        eventDao.delete(event.getId());
    }
    
}
