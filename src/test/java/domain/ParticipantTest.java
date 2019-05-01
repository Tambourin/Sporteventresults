
package domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olavi
 */
public class ParticipantTest {
    Contest contest;
    Event event = new Event("name", "location", LocalDate.now(), "info");
    
    public ParticipantTest() {
    }
    
    @Before
    public void setUp() {
        
        contest = new Contest(9999999, "name", LocalTime.now(), event);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void noArgsConstructorTest() {
        Participant p = new Participant();
        Integer expResult = -1;
        assertEquals(expResult, p.getBidNumber());
        assertEquals(null, p.getId());
    }
    
    @Test
    public void testGetId() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer id = 22345;
        p.setId(id);
        assertEquals(id, p.getId());
    }

    @Test
    public void testSetId() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        assertTrue(p.getId() == null);
        Integer id = 22345;
        p.setId(id);
        assertEquals(id, p.getId());
    }

    @Test
    public void testGetBidNumber() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer expResult = 99999;
        assertEquals(expResult, p.getBidNumber());
    }

    @Test
    public void testSetBidNumber() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Integer expResult = 99999;
        assertEquals(expResult, p.getBidNumber());
        expResult = 88888;
        p.setBidNumber(88888);
        assertEquals(expResult, p.getBidNumber());
    }

    @Test
    public void testGetFirstName() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "firstName";
        assertEquals(expResult, p.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "firstName";
        assertEquals(expResult, p.getFirstName());
        p.setFirstName("awefawefawef");
        expResult = "awefawefawef";
        assertEquals(expResult, p.getFirstName());        
    }

    @Test
    public void testGetLastName() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "lastName";
        assertEquals(expResult, p.getLastName());
    }

    @Test
    public void testSetLastName() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "lastName";
        assertEquals(expResult, p.getLastName());
        p.setLastName("awefawefawef");
        expResult = "awefawefawef";
        assertEquals(expResult, p.getLastName());  
    }

    @Test
    public void testGeteMail() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "eMail";
        assertEquals(expResult, p.geteMail());
    }

    @Test
    public void testSeteMail() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "eMail";
        assertEquals(expResult, p.geteMail());
        p.seteMail("awefawefawef");
        expResult = "awefawefawef";
        assertEquals(expResult, p.geteMail());  
    }

    @Test
    public void testGetPhone() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "phone";
        assertEquals(expResult, p.getPhone());
    }

    @Test
    public void testSetPhone() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "phone";
        assertEquals(expResult, p.getPhone());
        p.setPhone("awefawefawef");
        expResult = "awefawefawef";
        assertEquals(expResult, p.getPhone()); 
    }

    @Test
    public void testGetAddress() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "address";
        assertEquals(expResult, p.getAddress());
    }

    @Test
    public void testSetAddress() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "address";
        assertEquals(expResult, p.getAddress());
        p.setAddress("awefawefawef");
        expResult = "awefawefawef";
        assertEquals(expResult, p.getAddress()); 
    }

    @Test
    public void testGetClub() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "club";
        assertEquals(expResult, p.getClub());
    }

    @Test
    public void testSetClub() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        String expResult = "club";
        assertEquals(expResult, p.getClub());
        p.setClub("awefawefawef");
        expResult = "awefawefawef";
        assertEquals(expResult, p.getClub()); 
    }

    @Test
    public void testGetContest() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Contest c = p.getContest();
        assertEquals(contest, c);
    }

    @Test
    public void testSetContest() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ZERO, contest);
        Contest contest2 = new Contest("name", LocalTime.now(), event);
        p.setContest(contest2);
        Contest c = p.getContest();
        assertNotEquals(contest, c);
        assertEquals(contest2, c);
    }

    @Test
    public void testGetRaceResult() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        Duration expResult = Duration.ofSeconds(22);
        assertEquals(expResult, p.getRaceResult());
    }

    @Test
    public void testSetRaceResult() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        Duration expResult = Duration.ofSeconds(22);
        assertEquals(expResult, p.getRaceResult());
        p.setRaceResult(Duration.ofSeconds(88));
        expResult = Duration.ofSeconds(88);
        assertEquals(expResult, p.getRaceResult());
    }

    @Test
    public void testToString() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        String result = p.toString();
        String expResult = "99999 firstName lastName eMail phone address club PT22S name";
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        Participant p2 = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        p.setId(1);
        p2.setId(2);
        assertFalse(p.equals(p2));
        p2.setId(1);
        assertTrue(p.equals(p2));
        assertFalse(p.equals("ggggg"));
        
        assertTrue(p.equals(p));
    }

    @Test
    public void testEqualsForNull() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        assertFalse(p.equals(null));
    }
    
    @Test
    public void testHashCode() {
    }

    @Test
    public void testCompareTo() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        Participant p2 = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(11), contest);
        List<Participant> ps = new ArrayList<>();
        ps.add(p);
        ps.add(p2);       
        Duration expResult = Duration.ofSeconds(22);
        assertEquals(expResult, ps.get(0).getRaceResult());
        Collections.sort(ps);
        expResult = Duration.ofSeconds(11);
        assertEquals(expResult, ps.get(0).getRaceResult());
    }
    
    @Test
    public void testCompareToNotSameClass() {
        Participant p = new Participant(99999, "firstName", "lastName", "eMail", "phone", "address", "club", Duration.ofSeconds(22), contest);
        int ExpResult = -1;
        assertEquals(ExpResult, p.compareTo(null));
        assertEquals(ExpResult, p.compareTo("String"));
    }
    
}
