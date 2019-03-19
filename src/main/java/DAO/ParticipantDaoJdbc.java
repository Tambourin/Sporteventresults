/**
 * A CRUD-class to handle Participant data
 */
package DAO;

import domain.Contest;
import domain.Participant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Olavi
 */
public class ParticipantDaoJdbc implements ParticipantDao{
    
    
    /**
     * Inserts a record into the Participant table using values of
     * paticipant object that is passed to the method.
     * @param participant Participant whose values are inserted into table.
     */
    @Override
    public void create(Participant participant) {
        String sqlQuery = 
                "INSERT INTO "
                + "Participant(bidNumber, firstName, lastName, eMail, phone, address, club, raceResult, contestId) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
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
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);){ 
            DaoUtil.setValues(stmt, values);            
            stmt.executeUpdate(); 
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
    }

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
            1,
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

    @Override
    public Participant findByName(String name) {
        String sqlQuery = 
                "SELECT * FROM Participant "                
                + "WHERE UPPER(firstName) LIKE UPPER(?) "
                + "OR UPPER(lastName) LIKE UPPER(?)";
        Participant participant = null;
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, "%" + name + "%", "%" + name + "%");
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
    
    @Override
    public List<Participant> listByContest(Contest contest) {
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
