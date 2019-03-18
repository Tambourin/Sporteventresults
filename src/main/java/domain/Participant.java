
package domain;

import java.time.Duration;
import java.time.LocalTime;

/**
 *
 * @author Olavi
 */
public class Participant {
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
        this.firstName = "unnamed";
        this.lastName = "unnamed";
        this.eMail = "not available";
        this.phone = "not available";
        this.address = "not available";
        this.club = "not available";
        this.raceResult = Duration.ofSeconds(0, 0);
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
        return firstName + " " 
                + lastName + " "
                + eMail + " "
                + phone + " "
                + address + " "
                + club + " "
                + raceResult.toString() + " "
                + contest;
    }

    
   

   
    
}
