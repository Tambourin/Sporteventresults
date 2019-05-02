/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import services.ContestService;
import services.ParticipantService;
import dao.ContestDao;
import dao.ContestDaoJdbc;
import dao.EventDaoJdbc;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Event;
import domain.Participant;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olavi
 */
public class ContestServiceTest {
    ParticipantService ps = new ParticipantService();
    ContestService cs = new ContestService();
    ContestDao cDao = new ContestDaoJdbc();
    ParticipantDaoJdbc pDao = new ParticipantDaoJdbc();
    EventDaoJdbc eDao = new EventDaoJdbc();
    Event event = new Event("name", "location", LocalDate.now(), "info");
    Integer eventID;
    
    public ContestServiceTest() {
    }
    
    @Before
    public void setUp() {
        eventID = eDao.create(event);
        event.setId(eventID);
    }
    
    @After
    public void tearDown() {
        eDao.delete(eventID);
    }

    @Test
    public void testDelete() {        
        Integer id = cs.addNew("name", "20:45", event);
        Contest contest = cDao.findById(id);
//        //Participant p = new Participant(2222, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
//        //Integer pId = pDao.create(p);
        assertFalse(contest == null);
        cs.delete(contest);        
        assertEquals(null, cDao.findById(id)); 
//        //pDao.delete(pId);
//        cs.delete(contest);
//        contest = cDao.findById(id);
//        assertFalse(contest == null);
//        
//        if(cDao.findById(id) != null) {
            cDao.delete(id);
//        }        
    }
    
    @Test
    public void testDeleteIfNotEmpty() {
        Integer id = cs.addNew("name", "20:45", event);
        Contest contest = cDao.findById(id);
        Participant p = new Participant(2222, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer pId = pDao.create(p);
        p.setId(pId);
        assertNotEquals(null, cDao.findById(id));
        try {
            cs.delete(contest);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
        assertNotEquals(null, cDao.findById(id));
        pDao.delete(pId);
        cs.delete(contest);
        assertEquals(null, cDao.findById(id));
    }
    
    @Test
    public void testDeleteNull() {        
        assertFalse(cs.delete(null));
    }

    @Test
    public void testUpdate() {
        Integer id = cs.addNew("name", "20:45", event);
        Contest c = cDao.findById(id);
        assertFalse(c.getName().equals("hik"));
        c.setName("hik");
        cs.update(c);
        assertTrue(cDao.findById(id).getName().equals("hik"));
        assertEquals(false, cs.update(null));
        
        cDao.delete(id);
    }

    @Test
    public void testAddNew() {
        Integer id = cs.addNew("name", "20:45", event);
        assertTrue(cDao.findById(id) != null);
        assertEquals(null, cs.addNew("name", "343w6236", event));
        cDao.delete(id);
    }

    @Test
    public void testFindAll() {
        Integer id = cs.addNew("rshsthsedrhsehserhserh", "20:45", event);
        Integer id2 = cs.addNew("name2", "20:45", event);
        List<Contest> contests = cs.findAll();
        assertTrue(contests.size() > 0);
        Boolean found = false;
        for (Contest c : contests) {
            if (c.getName().equals("rshsthsedrhsehserhserh")) {
                found = true;
            }
        }
        assertTrue(found);
        cDao.delete(id);
        cDao.delete(id2);
    }
    
}
