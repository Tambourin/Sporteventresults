
package Services;

import dao.ParticipantDao;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Participant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for operations related to Participant entity
 * 
 * @author Olavi
 */
public class ParticipantService {
    private final ParticipantDao participantDao = new ParticipantDaoJdbc();
   
    /**
     * Searches participants who match by name or bidnumber
     * @param searchWord Search string, can contain participant's name or bidnumber;
     * @return Returns list of participants whose name or number matches searchWord 
     */   
    public List<Participant> findByNameOrNumber(String searchWord){       
        List<Participant> foundParticipants = new ArrayList<>();
        foundParticipants.addAll(participantDao.findByNameLike(searchWord));
        try {
            Participant p = participantDao.findByBidNumber(Integer.parseInt(searchWord));
            foundParticipants.add(p);
        } catch (Exception e) {
            System.out.println("Search: not a number");
        }       
        return foundParticipants;
    }
    
    /**
     * Returns a list of participants who participate in the given contest
     * @param contest that is used for search
     * @return List of participants in a contest specified by the parameter
     */
    public List<Participant> findByContest(Contest contest) {
        return participantDao.listByContest(contest);
    }
    
    /**
     * Deletes a participant from the database
     * @param participant to be deleted
     */
    public void delete(Participant participant) {
        participantDao.delete(participant.getId());
    }
    
    /**
     * Updates a participant's data in the databese
     * @param participant Participant whose data needs to be updated
     */
    public void update(Participant participant) {
        participantDao.update(participant);
    }
    
    /**
     * Creates a new participant to database
     * @param participant Participant to be added to database 
     */
    public void create(Participant participant) {
        participantDao.create(participant);
    }
    
    /**
     * Sets values to a given participant
     * @param participant Participant where values should be set
     * @param bidNumber
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @param address
     * @param club
     * @param contest
     */
    public void prepareParticipant(Participant participant, String bidNumber, String firstName, 
            String lastName, String email, String phone, String address, 
            String club, Contest contest){ 
        participant.setBidNumber(Integer.parseInt(bidNumber));
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.seteMail(email);
        participant.setPhone(phone);
        participant.setAddress(address);
        participant.setClub(club);
        participant.setContest(contest);
    }
    /**
     * Returns list of all participants in database
     * @return 
     */
    public List<Participant> findAll() {
        return participantDao.listAll();
    }
    
    /**
     * Checks if bidnumber is already assigned to other participant
     * @param bidNumber
     * @return Returns true if number was already in use. Otherwise returns false; 
     */
    public boolean bidNumberAlreadyInUse(String bidNumber){
       List<Participant> foundParticipants = findByNameOrNumber(bidNumber);
       if (foundParticipants.isEmpty() || foundParticipants.contains(null)) {
           return false;
       }
       return true;
   }
    
    /**
     * Parses a String in form of "hh:mm:ss" to Duration and returns it if string
     * could be parsed. Otherwise returns null.
     * @param original String to be parsed. Expected form is "hh:mm:ss".
     * @return Returns java.time.
     */
    public Duration parseDuration(String original) {
        String parsed = "PT";
        String[] parts = original.split(":");
        if (parts.length == 3) {
            parsed += parts[0] + "H" + parts[1] + "M" + parts[2] + "S";
        } else if (parts.length == 2) {
            parsed += parts[0] + "M" + parts[1] + "S";
        } else {
            return null;
        }      
        System.out.println(parsed);
        try {
            return Duration.parse(parsed);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }        
    }
}
