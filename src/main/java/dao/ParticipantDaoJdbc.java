/**
 * A CRUD-class to handle Participant data
 */
package dao;

import domain.Contest;
import domain.Event;
import domain.Participant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * Dao class responsible for database operations related to participant entity 
 * @author Olavi
 */
public class ParticipantDaoJdbc implements ParticipantDao{
    
    
    /**
     * Inserts a record into the Participant table using values of
     * paticipant object that is passed to the method.
     * @param participant Participant whose values are inserted into table.
     * @return Returns the id of created participant
     */
    @Override
    public Integer create(Participant participant) {
        String sqlQuery = 
                "INSERT INTO "
                + "Participant(bidNumber, firstName, lastName, eMail, phone, address, club, raceResult, contestId) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        Integer id = null;
        Object[] values = {
            participant.getBidNumber(),
            participant.getFirstName(),
            participant.getLastName(),
            participant.geteMail(),
            participant.getPhone(),
            participant.getAddress(),
            participant.getClub(),
            participant.getRaceResult().toString(),
            participant.getContest().getId()                
        };           
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = 
                    conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);){ 
            DaoUtil.setValues(stmt, values);            
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        return id;
    }

    /**
     * Updates a rocord in database
     * @param participant participant instance whose data is updated
     */
    @Override
    public void update(Participant participant){
        String sqlQuery = 
                "UPDATE Participant SET bidNumber = ?, firstName = ?, lastName = ?, eMail = ?, "
                + "phone = ?, address = ?, club = ?, raceResult = ?, contestId = ?"
                + "WHERE id = ?";
        Object[] values = {
            participant.getBidNumber(),
            participant.getFirstName(),
            participant.getLastName(),
            participant.geteMail(),
            participant.getPhone(),
            participant.getAddress(),
            participant.getClub(),
            participant.getRaceResult().toString(),
            participant.getContest().getId(),
            participant.getId()
        }; 
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {            
            DaoUtil.setValues(stmt, values);
            stmt.executeUpdate(); 
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
    }

    /**
     * Deletes a record with given id from the Participant table 
     * @param key ID key for a record to be deleted.
     */
    
    @Override
    public void delete(Integer key) {
        String sqlQuery = 
                "DELETE FROM Participant WHERE id = ?";
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, key);
            stmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace(); 
        }
    }

    /**
     * Uses key parameter to find a certain participant from Participant table.
     * @param key
     * @return 
     */
    @Override
    public Participant findById(Integer key) {
        String sqlQuery = 
                "SELECT * FROM Participant WHERE id = ?";                
        Participant participant = null;                
        try (
            Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {            
            DaoUtil.setValues(stmt, key);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                return null;
            }
            participant = prepareParticipant(rs);
        } catch(SQLException ex){
            ex.printStackTrace(); 
        }        
        return participant;
    }

    /**
     * Seeks for a record in database where name field is like parameter
     * @param name
     * @return Returns a list of participants whose name is like param
     */
    @Override
    public List<Participant> findByNameLike(String name, Event event) {
        String sqlQuery = 
                "SELECT * FROM Participant "
                + "JOIN Contest ON Participant.contestId = Contest.id "
                + "JOIN Event On Contest.eventId = Event.id "                
                + "WHERE UPPER(firstName) LIKE UPPER(?) "
                + "OR UPPER(lastName) LIKE UPPER(?) "
                + "AND Event.id = ?";
        List<Participant> participants = new ArrayList<>();
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, "%" + name + "%", "%" + name + "%", event.getId());
            ResultSet rs = stmt.executeQuery();            
            while(rs.next()) {
                if (rs != null) {                    
                    Participant p = prepareParticipant(rs);  
                    participants.add(p);
                }
            }
        } catch(SQLException ex){
            ex.printStackTrace(); 
        }
        return participants;
    }
    
    /**
     * Returns a participant whose bidnumber matches param
     * @param bidNumber
     * @return 
     */
    @Override
    public Participant findByBidNumber(Integer bidNumber, Event event) {
        if (bidNumber == null) {
            return null;
        }
        String sqlQuery = "SELECT * FROM Participant "
                + "JOIN Contest ON Participant.contestId = Contest.id "
                + "JOIN Event ON Contest.eventId = Event.id "
                + "WHERE bidNumber = ? AND Event.id = ?";
        Participant participant = null;
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, bidNumber, event.getId());
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                return null;
            }
            participant = prepareParticipant(rs);
        } catch(SQLException ex){
            ex.printStackTrace(); 
        }
        return participant;
    }

    /**
     * Return a list of all participants in the database
     * @return List of all participants
     */
    @Override
    public List<Participant> listAll() {
        String sqlQuery = "SELECT * FROM Participant";
        List<Participant> participants = new ArrayList<>();
       
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Participant p = prepareParticipant(rs);
                participants.add(p);
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }          
        return participants;
    }
    
    /**
     * Finds participants whose contest equals param
     * @param contest
     * @return Returns a list of participants whose contest equals param
     */
    @Override
    public List<Participant> listByContest(Contest contest) {
        if (contest == null) {
            return null;
        }
        String sqlQuery = "SELECT * FROM Participant WHERE ContestID = ?";
        List<Participant> participants = new ArrayList<>();
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            DaoUtil.setValues(stmt, contest.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                participants.add(prepareParticipant(rs));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return participants;
    } 

    /**
     * Counts participants in a given contest.
     * @param contest
     * @return Return a number of participants in particular contest
     */
    public Integer countByContest(Contest contest) {
        String sqlQuery = "SELECT COUNT(id) AS number FROM Participant WHERE ContestID = ?";
        Integer numberOfParticipantsInContest = 0;
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            DaoUtil.setValues(stmt, contest.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                numberOfParticipantsInContest = rs.getInt("number");                
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return numberOfParticipantsInContest;
    }
    
    private Participant prepareParticipant(ResultSet rs) throws SQLException{
        Participant participant = new Participant();
        participant.setId(rs.getInt("id"));
        participant.setBidNumber(rs.getInt("bidNumber"));
        participant.setFirstName(rs.getString("firstName"));
        participant.setLastName(rs.getString("lastName"));
        participant.seteMail(rs.getString("eMail"));
        participant.setPhone(rs.getString("phone"));
        participant.setAddress(rs.getString("address"));
        participant.setClub(rs.getString("club"));
        if (rs.getString("raceResult") != null) {
            participant.setRaceResult(Duration.parse(rs.getString("raceResult")));
        } else {
            participant.setRaceResult(Duration.ofSeconds(0));
        }
        participant.setContest(new ContestDaoJdbc().findById(rs.getInt("contestId")));
        return participant;
    }

    




}
