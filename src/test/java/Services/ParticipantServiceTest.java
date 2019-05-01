/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import dao.ContestDao;
import dao.ContestDaoJdbc;
import dao.EventDaoJdbc;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Event;
import domain.Participant;
import java.time.Duration;
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
public class ParticipantServiceTest {
    ParticipantService ps = new ParticipantService();
    ContestDao cDao = new ContestDaoJdbc();
    ParticipantDaoJdbc pDao = new ParticipantDaoJdbc();
    EventDaoJdbc eDao = new EventDaoJdbc();
    Contest contest;
    Contest contest2;
    Integer contestId = null;
    Integer contest2Id = null;
    Integer eventId;
    Event event;
    
    public ParticipantServiceTest() {
    }
    
    @Before
    public void setUp() {
        event = new Event("name", "location", LocalDate.now(), "info");
        eventId = eDao.create(event);
        event.setId(eventId);
        contest = new Contest("poiuytrewq", LocalTime.of(20, 30), event);
        contest2 = new Contest("yjdyjdjdtj", LocalTime.of(20, 30), event);
        contestId = cDao.create(contest);
        contest.setId(contestId);
        contest2Id = cDao.create(contest);
        contest2.setId(contest2Id);
    }
    
    @After
    public void tearDown() {
        cDao.delete(contestId);
        cDao.delete(contest2Id);
        eDao.delete(eventId);
    }

    @Test
    public void testFindByNameOrNumber() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);
        assertTrue(ps.findByNameOrNumber("firstName", event).size() > 0);
        assertTrue(ps.findByNameOrNumber("777777", event).size() > 0);
        assertFalse(ps.findByNameOrNumber("asdfun46", event).size() > 0);
        pDao.delete(id);
    }

    @Test
    public void testFindByContest() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);
        assertTrue(ps.findByContest(contest).size() > 0);
        pDao.delete(id);
        assertTrue(ps.findByContest(contest).size() == 0);
    }

    @Test
    public void testDelete() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);
        participant.setId(id);
        assertTrue(pDao.findById(id) != null);
        ps.delete(participant);
        assertTrue(pDao.findById(id) == null);
        if(pDao.findById(id) != null) {
            pDao.delete(id);
        }        
    }

    @Test
    public void testSave() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id0 = pDao.create(participant);
        ps.save(id0, "844654615", "firstName", "lastName", "email", "phone", "address", "club", contest);
        assertTrue(pDao.findByBidNumber(844654615, event) != null);
        Integer id = ps.save(null, "345646", "firstName", "lastName", "email", "phone", "address", "club", contest);
        assertTrue(pDao.findById(id) != null);
        pDao.delete(id0);
        pDao.delete(id);
    }

    @Test
    public void testAddToFinished() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);
        participant.setId(id);
        ps.addToFinished(participant, null);
        assertNotEquals(Duration.parse("PT30H30M30S"), pDao.findById(id).getRaceResult());
        ps.addToFinished(participant, "30:30:30");        
        assertEquals(Duration.parse("PT30H30M30S"), pDao.findById(id).getRaceResult());
        pDao.delete(id);
    }

    @Test
    public void testUpdate() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);
        participant = pDao.findById(id);
        participant.setFirstName("111kueio3");
        ps.update(participant);
        assertTrue(pDao.findByNameLike("111kueio3", event).size() > 0);
        pDao.delete(id);
    }

    @Test
    public void testFindAll() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);
        Assert.assertTrue(pDao.listAll().size() > 0);
        List<Participant> allP = ps.findAll();
        Boolean found = false;
        for (Participant p : allP) {
            if (p.getBidNumber() == 777777) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        pDao.delete(id);
    }

    @Test
    public void testBidNumberAlreadyInUse() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);
        participant = pDao.findById(id);  
        Participant participant2 = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id2 = pDao.create(participant2);
        participant2 = pDao.findById(id);     
        //assertTrue(ps.bidNumberAlreadyInUse("777777", new Participant()));
        assertFalse(ps.bidNumberAlreadyInUse("777777", participant)); 
        assertFalse(ps.bidNumberAlreadyInUse("777", participant)); 
        pDao.delete(id);
        pDao.delete(id2);
    }
    @Test
    public void testBidNumberAlreadyInUseNull() {
        Participant participant = new Participant(null, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = pDao.create(participant);        
        assertFalse(ps.bidNumberAlreadyInUse(null, participant));
        pDao.delete(id);
    }

    @Test
    public void testParseDuration() {
        Duration duration = ps.parseDuration("30:30:30");
        assertEquals(Duration.parse("PT30H30M30S"), duration);
        duration = ps.parseDuration("30:30");
        assertEquals(Duration.parse("PT30M30S"), duration);
        duration = ps.parseDuration("30:30:30:30");
        assertEquals(null, duration);
        duration = ps.parseDuration("30:30efawefawefwef");
        assertEquals(null, duration);
    }
    
}
