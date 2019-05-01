
package Services;

import dao.EventDaoJdbc;
import domain.Event;
import java.util.List;

/**
 * Service class for operations related to Event entity
 * @author Olavi
 */
public class EventService {
    EventDaoJdbc eventDaoJdbc = new EventDaoJdbc();
    
    /**
     * Seeks and returns a Event based on id-key passed as parameter
     * @param id Event's id in database
     * @return Returns event
     */
    public Event findById(Integer id) {
        return eventDaoJdbc.findById(id);
    }
    
    public List<Event> findAll() {
        return eventDaoJdbc.findAll();
    }
    /**
     * Updates event's data in database
     * @param event Event to be updated
     */
    public void update(Event event) {
        eventDaoJdbc.update(event);
    }
    
    public Integer create(Event event) {
        return eventDaoJdbc.create(event);
    }
}
