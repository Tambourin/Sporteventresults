/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Contest;
import domain.Event;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class ContestDaoJdbcTest {
    ContestDao cDao = new ContestDaoJdbc();
    EventDaoJdbc eDao = new EventDaoJdbc();
    Event event;   
    Integer eventID;
    
    public ContestDaoJdbcTest() {
    }
    
    @Before
    public void setUp() {
        event = new Event("name", "location", LocalDate.now(), "info");
        eventID = eDao.create(event);
        event.setId(eventID);
        System.out.println("Eventin id: " + event.getId());
    }
    
    @After
    public void tearDown() {
        eDao.delete(eventID);
    }

    @Test
    public void testCreate() {
        System.out.println("Test create");
        Contest c = new Contest("gaskdjghisawgen", LocalTime.now(), event);
        Integer id = cDao.create(c);
        Assert.assertFalse(id == null);
        List<Contest> allC = cDao.findAll();
        Boolean found = false;
        for (Contest cont : allC) {
            if (cont.getName().equals("gaskdjghisawgen")) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        cDao.delete(id);
    }

    @Test
    public void testUpdate() {
        Contest c = new Contest("gaskdjghisawgen", LocalTime.now(), event);
        System.out.println(c.getEvent().getId());
        Integer id = cDao.create(c);
        Contest contest = cDao.findById(id);
        System.out.println(contest.getEvent().getId());
        contest.setName("hhhhhlhlhlhlhlohlh");
        System.out.println(contest.getEvent().getId());
        cDao.update(contest);
        assertTrue(cDao.findById(id).getName().equals("hhhhhlhlhlhlhlohlh"));
        cDao.delete(id);
    }

    @Test
    public void testDelete() {
        Contest c = new Contest("gaskdjghisawgen", LocalTime.now(), event);
        Integer id = cDao.create(c);
        assertTrue(cDao.findById(id) != null);
        cDao.delete(id);
        assertFalse(cDao.findById(id) != null);
    }

    @Test
    public void testFindById() {
        Contest c = new Contest("gaskdjghisawgen", LocalTime.now(), event);
        Integer id = cDao.create(c);
        assertTrue(cDao.findById(id) != null);
        cDao.delete(id);
    }
    
    @Test
    public void testFindByEvent() {
        Contest c = new Contest("gaskdjghisawgen", LocalTime.now(), event);
        Integer id = cDao.create(c);
        assertTrue(cDao.findById(id) != null);
        cDao.delete(id);
    }

    @Test
    public void testFindAll() {
        Contest c = new Contest("gaskdjghisawgen", LocalTime.now(), event);
        Integer id = cDao.create(c);
        assertTrue(cDao.findAll().size() > 0);
        List<Contest> allC = cDao.findAll();
        Boolean found = false;
        for (Contest cont : allC) {
            if (cont.getName().equals("gaskdjghisawgen")) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        cDao.delete(id);
    }
    
}
