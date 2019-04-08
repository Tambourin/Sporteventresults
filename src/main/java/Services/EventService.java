
package Services;

import dao.EventDaoJdbc;
import domain.Event;

/**
 * Service class for operations related to Event entity
 * @author Olavi
 */
public class EventService {
    EventDaoJdbc eventDaoJdbc = new EventDaoJdbc();
    
    /**
     * Seeks and returns a Event based on id passed as parameter
     * @param id Event's id in database
     * @return Returns event
     */
    public Event findById(Integer id) {
        return eventDaoJdbc.findById(id);
    }
    
    /**
     * Updates event's data in database
     * @param event Event to be updated
     */
    public void update(Event event) {
        eventDaoJdbc.update(event);
    }
}
