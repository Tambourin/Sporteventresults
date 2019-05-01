package dao;

import domain.Contest;
import domain.Event;
import domain.Participant;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Olavi
 */
public class ParticipantDaoJdbcTest {
    Contest contest;
    Contest contest2;
    ParticipantDaoJdbc dao;
    ContestDao cDao;
    EventDaoJdbc eDao = new EventDaoJdbc();
    Integer contestId = null;
    Integer contest2Id = null;
    Integer eventId;
    Event event;
    
    public ParticipantDaoJdbcTest() {
    }

    @Before
    public void setUp() {
        event = new Event("name", "location", LocalDate.now(), "info");
        eventId = eDao.create(event);
        event.setId(eventId);
        contest = new Contest("poiuytrewq", LocalTime.of(20, 30), event);
        contest2 = new Contest("yjdyjdjdtj", LocalTime.of(20, 30), event);
        dao = new ParticipantDaoJdbc();
        cDao = new ContestDaoJdbc();
        contestId = cDao.create(contest);
        contest.setId(contestId);
        contest2Id = cDao.create(contest);
        contest2.setId(contest2Id);
    }
    @After
    public void clean() {
        cDao.delete(contestId);
        cDao.delete(contest2Id);
        eDao.delete(eventId);
    }
    
    @Test
    public void testCreate() {        
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Assert.assertFalse(id == null);
        List<Participant> allP = dao.listAll();
        Boolean found = false;
        for (Participant p : allP) {
            if (p.getBidNumber() == 777777) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        dao.delete(id);
    }

    @Test
    public void testUpdate() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Participant p = dao.findById(id);
        p.setFirstName("111kueio3");
        dao.update(p);
        Assert.assertTrue(dao.findByNameLike("111kueio3", participant.getContest().getEvent()).size() > 0);
        dao.delete(id);
    }

    @Test
    public void testDelete() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Assert.assertTrue(dao.findById(id) != null);
        dao.delete(id);
        Assert.assertFalse(dao.findById(id) != null);
    }
    


    @Test
    public void testFindById() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        assertTrue(dao.findById(id) != null);
        dao.delete(id);
    }

    @Test
    public void testFindByNameLike() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Assert.assertTrue(dao.findByNameLike("FIRSTNAME", event).size() > 0);
        Assert.assertFalse(dao.findByNameLike("FISfasdfaTNE", event).size() > 0);
        dao.delete(id);
    }

    @Test
    public void testFindByBidNumber() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Assert.assertTrue(dao.findByBidNumber(777777, event) != null);
        Assert.assertFalse(dao.findByBidNumber(888838838, event) != null);
        dao.delete(id);
    }
    
    @Test
    public void testFindByBidNumberNull() {
        assertEquals(null, dao.findByBidNumber(null, null));
    }

    @Test
    public void testListAll() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Assert.assertTrue(dao.listAll().size() > 0);
        List<Participant> allP = dao.listAll();
        Boolean found = false;
        for (Participant p : allP) {
            if (p.getBidNumber() == 777777) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        dao.delete(id);
    }

    @Test
    public void testListByContest() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Assert.assertTrue(dao.listAll().size() > 0);
        List<Participant> allP = dao.listByContest(contest);
        Boolean found = false;
        for (Participant p : allP) {
            if (p.getBidNumber() == 777777) {
                found = true;
            }
        }
        List<Participant> allInWrongContest = dao.listByContest(contest2);
        Boolean foundWrong = false;
        for (Participant p : allInWrongContest) {
            if (p.getBidNumber() == 777777) {
                foundWrong = true;
            }
        }
        
        Assert.assertTrue(found);
        Assert.assertFalse(foundWrong);
        dao.delete(id);
    }
    
    @Test
    public void testListByContestNull() {
        assertEquals(null, dao.listByContest(null));
    }

    @Test
    public void testCountByContest() {
        Participant participant = new Participant(777777, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = dao.create(participant);
        Integer expResult = 1;
        Assert.assertEquals(expResult, dao.countByContest(contest));
        Assert.assertNotEquals(expResult, dao.countByContest(contest2));
        dao.delete(id);
    }
    
}
