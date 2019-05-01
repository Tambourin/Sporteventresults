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
public class EventDaoJdbcTest {
    EventDaoJdbc eDao;
    
    public EventDaoJdbcTest() {
    }
    
    @Before
    public void setUp() {
        eDao = new EventDaoJdbc();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreate() {
        Event e = new Event("aowsefiaowefnwef", "location", LocalDate.now(), "info");
        Integer id = eDao.create(e);
        Assert.assertTrue(eDao.findById(id) != null);
        eDao.delete(id);
    }

    @Test
    public void testUpdate() {
        Event e = new Event("aowsefiaowefnwef", "location", LocalDate.now(), "info");
        Integer id = eDao.create(e);
        e.setId(id);
        Assert.assertEquals("info", eDao.findById(id).getInfo());
        e.setInfo("argaewhrh");
        eDao.update(e);
        Assert.assertEquals("argaewhrh", eDao.findById(id).getInfo());
        eDao.delete(id);
    }

    @Test
    public void testFindById() {
        Event e = new Event("aowsefiaowefnwef", "location", LocalDate.now(), "info");
        Integer id = eDao.create(e);
        Assert.assertTrue(eDao.findById(id) != null);
        eDao.delete(id);
    }
    
    @Test
    public void testDelete() {
        Event e = new Event("aowsefiaowefnwef", "location", LocalDate.now(), "info");
        Integer id = eDao.create(e);
        Assert.assertTrue(eDao.findById(id) != null);
        eDao.delete(id);
        Assert.assertFalse(eDao.findById(id) != null);
    }
    
    @Test
    public void testFindAll() {
        Event e = new Event("testFindAll()namrgsdrgsergserge", "location", LocalDate.now(), "info");
        Integer id = eDao.create(e);
        e.setId(id);
        Assert.assertTrue(eDao.findAll().size() > 0);
        List<Event> allE = eDao.findAll();
        Boolean found = false;
        for (Event ev : allE) {
            System.out.println(ev);
            //assertFalse(ev == null);
            if (ev.getName().equals("testFindAll()namrgsdrgsergserge")) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        eDao.delete(id);
    }
}
