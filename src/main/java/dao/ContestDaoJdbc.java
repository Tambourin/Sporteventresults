
package dao;

import domain.Contest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao class responsible for database operations related to contest entity 
 * @author Olavi
 */
public class ContestDaoJdbc implements ContestDao{

    /**
     * Creates a record to database accordind to param
     * @param contest
     */
    @Override
    public void create(Contest contest) {
        String sqlQuery = "INSERT INTO Contest(name, startingTime) VALUES (?,?)";
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);) {
            String[] values = {
                contest.getName(), contest.getStartingTime().toString()
            };
            DaoUtil.setValues(stmt, values);
            stmt.executeUpdate();
  
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
    }

    /**
     * Updates a database record based on param
     * @param contest
     */
    @Override
    public void update(Contest contest) {
        String sqlQuery = "UPDATE Contest SET name = ?, startingTime = ? "
                + "WHERE id = ?";
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {            
            DaoUtil.setValues(stmt, contest.getName(), contest.getStartingTime(), contest.getId());
            stmt.executeUpdate(); 
            
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
      
    }

    /**
     * Deletes a database record
     * @param key An id of record to be deleted
     */
    @Override
    public void delete(Integer key) {
        String sqlQuery = "DELETE FROM Contest WHERE id = ?";
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, key);
            stmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace(); 
        }
    }

    /**
     * Seeks for a contest according to it's id
     * @param key
     * @return Returns the found contest. If no contest if found, returns null
     */
    @Override
    public Contest findById(Integer key) {
        String sqlQuery = "SELECT * FROM Contest WHERE id = ?";
        Contest contest = null;
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, key);  
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                return null;
            }
            contest = prepareContest(rs);
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        return contest;
    }

    /**
     * Finds all records in Contest table
     * @return Returns list of all contests in database
     */
    @Override
    public List<Contest> findAll() {
        String sqlQuery = "SELECT * FROM Contest";
        List<Contest> contests = new ArrayList();
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                contests.add(prepareContest(rs));
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        return contests;
    }

    private Contest prepareContest(ResultSet rs) throws SQLException{
        Contest contest = new Contest();
        contest.setId(rs.getInt("id"));
        contest.setName(rs.getString("name"));
        if (rs.getString("StartingTime") != null) {
            contest.setStartingTime(LocalTime.parse(rs.getString("StartingTime")));
        } else {
            contest.setStartingTime(LocalTime.of(0, 0));
        }        
        contest.setParticipantsNumber(new ParticipantDaoJdbc().countByContest(contest));
        
        return contest;
    }
    
}
