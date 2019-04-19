
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
     * Create a new participant to database or update an existing one.
     * If the partisipant does not have an id attribute(it is not in the database),
     * add a new participant to database otherwise update existing one. 
     * @param id
     * @param bidNumber
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @param address
     * @param club
     * @param contest 
     */   
    public void save(Integer id, String bidNumber, String firstName, 
            String lastName, String email, String phone, String address, 
            String club, Contest contest) {
        Participant participant = new Participant();
        participant.setId(id);
        participant.setBidNumber(Integer.parseInt(bidNumber));
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.seteMail(email);
        participant.setPhone(phone);
        participant.setAddress(address);
        participant.setClub(club);
        participant.setContest(contest);
        if (id == null) {
            participantDao.create(participant);
        } else {
            participantDao.update(participant);
        }
    }
    
    /**
     * Sets participant's race result when finished the race.
     * @param participant Partisipant who has finished
     * @param durationString Result time in String format
     */
    public void addToFinished(Participant participant, String durationString){
        Duration duration = parseDuration(durationString);
        if (duration != null) {
           participant.setRaceResult(duration);
           participantDao.update(participant); 
        }        
    }
    /**
     * Updates a participant's data in the databese
     * @param participant Participant whose data needs to be updated
     */
    public void update(Participant participant) {
        participantDao.update(participant);
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
     * @return Returns the participant to whom the number was associated or
     * null if no participant with same number was found.
     */
    public Participant bidNumberAlreadyInUse(String bidNumber){
       List<Participant> foundParticipants = findByNameOrNumber(bidNumber);
       return foundParticipants.get(0);
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
