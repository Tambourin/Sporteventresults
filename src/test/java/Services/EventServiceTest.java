/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import dao.EventDaoJdbc;
import domain.Event;
import domain.Participant;
import java.time.LocalDate;
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
}
