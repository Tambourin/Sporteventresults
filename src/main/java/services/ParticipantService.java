
package services;

import dao.ContestDaoJdbc;
import dao.EventDaoJdbc;
import dao.ParticipantDao;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Event;
import domain.Participant;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service class for operations related to Participant entity
 * 
 * @author Olavi
 */
public class ParticipantService {
    private final ParticipantDao participantDao = new ParticipantDaoJdbc();
    private final ContestDaoJdbc contestDao = new ContestDaoJdbc();
    /**
     * Searches participants who match by name or bidnumber
     * @param searchWord Search string, can contain participant's name or bidnumber;
     * @return Returns list of participants whose name or number matches searchWord 
     */   
    public List<Participant> findByNameOrNumber(String searchWord, Event event){       
        List<Participant> foundParticipants = new ArrayList<>();
        foundParticipants.addAll(participantDao.findByNameLike(searchWord, event));
        try {
            Participant p = participantDao.findByBidNumber(Integer.parseInt(searchWord), event);
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
    
    public List<Participant> findByEvent(Event event) {
        System.out.println("findByEvent" + event);
        List<Contest> contests = contestDao.findAllByEvent(event);
        List<Participant> participants = new ArrayList<>();
        contests.forEach(contest -> {
            participants.addAll(participantDao.listByContest(contest)); 
        });
        
        return participants;
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
    public Integer save(Integer id, String bidNumber, String firstName, 
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
            return participantDao.create(participant);            
        } else {
            participantDao.update(participant);
            return id;
        }
    }
    
    /**
     * Sets participant's race result when finished the race.
     * @param participant Partisipant who has finished
     * @param durationString Result time in String format
     */
    public void addToFinished(Participant participant, String durationString){        
        if (durationString != null) {
           Duration duration = parseDuration(durationString);
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
     * @return List of all participants
     */
    public List<Participant> findAll() {
        return participantDao.listAll();
    }
    
    /**
     * Checks if bidnumber is already assigned to other participant
     * @param bidNumber number to check
     * @param participant Participant context
     * @return Returns true if database already contains a participant with
     * the given number. If number is not found or if the context participant does not have a bid number,
     * or if the number is the same as context participant's 
     * (ie. we are updating a participant without changing it's number then retun false.
     */
    public boolean bidNumberAlreadyInUse(String bidNumber, Participant participant){
        if (participant.getBidNumber() == null) {
            return false;
        }
        Integer num;
        try {
            num = Integer.parseInt(bidNumber);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        if (Objects.equals(num, participant.getBidNumber())) {
            return false;
        }
        List<Participant> foundParticipants 
                = findByNameOrNumber(bidNumber, participant.getContest().getEvent());
        if (foundParticipants.size() > 0 && foundParticipants.get(0) != null) {
            System.out.println(foundParticipants);
            return true;
        }
        return false;
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
