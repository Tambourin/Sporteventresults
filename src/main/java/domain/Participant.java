
package domain;

import java.time.Duration;
import java.util.Objects;

/**
 * Participant takes part in contest, which in turn is part of an event.
 * Participants in contests are unique. No participant data is saved separately.
 * If the same participant takes part in different contest or event, new participant
 * must be created.
 */
public class Participant implements Comparable{
    private Integer id;
    private Integer bidNumber;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private String address;
    private String club;    
    private Duration raceResult;
    private Contest contest;  //The contest that the participant takes part in

    public Participant() {
        this.bidNumber = -1;
        this.firstName = "";
        this.lastName = "";
        this.eMail = "";
        this.phone = "";
        this.address = "";
        this.club = "";
        this.raceResult = Duration.ofSeconds(0, 0);
    }

    public Participant(Integer bidNumber, String firstName, String lastName, String eMail, String phone, String address, String club, Duration raceResult, Contest contest) {
        this.bidNumber = bidNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.phone = phone;
        this.address = address;
        this.club = club;
        this.raceResult = raceResult;
        this.contest = contest;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBidNumber() {
        return bidNumber;
    }

    public void setBidNumber(Integer bidNumber) {
        this.bidNumber = bidNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Duration getRaceResult() {
        return raceResult;
    }

    public void setRaceResult(Duration raceResult) {
        this.raceResult = raceResult;
    }

    @Override
    public String toString() {
        return 
                bidNumber + " "
                + firstName + " "
                + lastName + " "
                + eMail + " "
                + phone + " "
                + address + " "
                + club + " "
                + raceResult.toString() + " "
                + contest;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Participant p = (Participant) obj;
        return p.id == this.id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass().equals(this.getClass())) {
            Participant p = (Participant) o;
            return (int) (this.raceResult.getSeconds()- p.raceResult.getSeconds());
        }
        return -1;
    }

    
   

   
    
}
