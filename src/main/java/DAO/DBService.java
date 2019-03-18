
package DAO;

import domain.Contest;
import domain.Participant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Handles database communication using DAO-classes
 */

public class DBService {
    
    ParticipantDao participantDao;
    ContestDao contestDao;
    
    
    public DBService() {
        //Instantiate Dao Classes that take care of concrete database communication
        participantDao = new ParticipantDaoJdbc();
        contestDao = new ContestDaoJdbc();
    }
    
    
    public void addParticipant(Integer bidNumber, String firstName, 
            String lastName, String eMail, String phone, String address, 
            String club, Contest contest) {
        Participant participant = new Participant();
        participant.setBidNumber(bidNumber);
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.seteMail(eMail);
        participant.setPhone(phone);
        participant.setAddress(address);
        participant.setClub(club);
        participant.setContest(contest);
        participantDao.create(participant);
    }
    
    /** @return Returns all participants*/
    public ObservableList<Participant> getParticipants() {
        ObservableList participants = FXCollections.observableArrayList();
        participants.addAll(participantDao.listAll());
        return participants;
    }
    
    /** Searches participants that take part in a certain contest
     * @param contest that is used for search
     * @return list of participants in a contest that was set by the parameter
     */
    public ObservableList<Participant> getParticipants(Contest contest) {        
        ObservableList participants = FXCollections.observableArrayList();
        participants.addAll(participantDao.listByContest(contest));
        return participants;
    }
  
    /** 
     @return ObservableList of all Contests 
     */
    public ObservableList<Contest> getContests() {
        ObservableList contests = FXCollections.observableArrayList();
        contests.addAll(contestDao.listAll());
        return contests;
    }
    
    
}
