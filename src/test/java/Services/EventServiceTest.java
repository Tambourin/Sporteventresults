/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import services1.EventService;
import dao.ContestDaoJdbc;
import dao.EventDaoJdbc;
import dao.ParticipantDao;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Event;
import domain.Participant;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olavi
 */
public class EventServiceTest {
    EventDaoJdbc eventDaoJdbc = new EventDaoJdbc();
    ContestDaoJdbc contestDaoJdbc = new ContestDaoJdbc();
    ParticipantDaoJdbc participantDaoJdbc = new ParticipantDaoJdbc();
    EventService eS = new EventService();
    
    public EventServiceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFindById() {
        Event e = new Event("name", "location", LocalDate.now(), "info");
        Integer id = eventDaoJdbc.create(e);
        assertTrue(eS.findById(id).getName().equals("name"));
        eventDaoJdbc.delete(id);
    }

    @Test
    public void testUpdate() {
        Event e = new Event("name", "location", LocalDate.now(), "info");
        Integer id = eventDaoJdbc.create(e);
        e.setId(id);
        assertTrue(eS.findById(id).getName().equals("name"));
        e.setName("aswfawefawefawefwef");
        eS.update(e);
        assertTrue(eS.findById(id).getName().equals("aswfawefawefawefwef"));
        eventDaoJdbc.delete(id);
    }
    
    @Test
    public void testFindAll() {
        Event e = new Event("testFindAll()namrgsdrgsergserge", "location", LocalDate.now(), "info");
        Integer id = eventDaoJdbc.create(e);
        e.setId(id);
        Assert.assertTrue(eS.findAll().size() > 0);
        List<Event> allE = eS.findAll();
        Boolean found = false;
        for (Event ev : allE) {
            System.out.println(ev);
            assertFalse(ev == null);
            if (ev.getName().equals("testFindAll()namrgsdrgsergserge")) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        eventDaoJdbc.delete(id);
    }
    
    @Test
    public void testDeleteEventAndIncludedContests() {
        Event e = new Event("name", "location", LocalDate.of(2009, Month.MARCH, 5), "info");
        e.setId(eventDaoJdbc.create(e));
        Contest c = new Contest("name", LocalTime.of(1, 10), e);
        c.setId(contestDaoJdbc.create(c));
        Participant p = new Participant(9869869, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, c);
        p.setId(participantDaoJdbc.create(p));

        assertTrue(participantDaoJdbc.listAll().contains(p));
        assertTrue(contestDaoJdbc.findAll().contains(c));
        assertTrue(eventDaoJdbc.findAll().contains(e));
        eS.deleteEventAndAssociatedContests(e);
        assertFalse(participantDaoJdbc.listAll().contains(p));
        assertFalse(contestDaoJdbc.findAll().contains(c));
        assertFalse(eventDaoJdbc.findAll().contains(e));
        
    }
}
