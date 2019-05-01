
package dao;

import domain.Contest;
import domain.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao class responsible for database operations related to event entity 
 * @author Olavi
 */
public class EventDaoJdbc {
    
    /**
     * Create a record to database.
     * @param event 
     * @return  Returns id of just created event or null if failed.
     */
    public Integer create(Event event) {
        String sqlQuery = "INSERT INTO Event(name, date, location, info) "
                + "VALUES(?, ?, ?, ?)";
        Integer id = null;
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = 
                        conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);) {
            Object[] values = {
                event.getName(), event.getDate().toString(),
                event.getLocation(), event.getInfo()};
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
     * Update a record in database
     * @param event 
     */
    public void update(Event event) {
        String sqlQuery = "UPDATE Event SET name=?, date=?, location=?, info=? "
                + "WHERE id = ?";
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            Object[] values = {
                event.getName(), event.getDate().toString(), event.getLocation(),
                event.getInfo(), event.getId()};
            DaoUtil.setValues(stmt, values);
            stmt.executeUpdate();
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
    }
    
    /**
     * Find a record from database based on records id
     * @param id Event id 
     * @return Returns found event. If no event is found, return null.
     */
    public Event findById(Integer id) {
        String sqlQuery = "SELECT * FROM Event WHERE id=?";
        Event event = null;
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, id);            
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                return null;
            }            
            event = prepareEvent(rs);
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        return event;
    }
    
        /**
     * Finds all records in Event table
     * @return Returns list of all events in database
     */
    public List<Event> findAll() {
        String sqlQuery = "SELECT * FROM Event";
        List<Event> events = new ArrayList();
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(prepareEvent(rs));
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        return events;
    }
    
    /**
     * Deletes a database record
     * @param key An id of record to be deleted
     */
    public void delete(Integer key) {
        String sqlQuery = "DELETE FROM Event WHERE id = ?";
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt, key);
            stmt.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace(); 
        }
    }
    
    
    private Event prepareEvent(ResultSet rs) throws SQLException {
//        if (!rs.next()) {
//                return null;
//        }
        Event event = new Event();
        event.setId(rs.getInt("id")); 
        event.setName(rs.getString("name"));
        event.setLocation(rs.getString("location")); 
        if (rs.getString("date") != null) {
            event.setDate(LocalDate.parse(rs.getString("date")));
        } else {
            event.setDate(LocalDate.now());
        }                
        event.setInfo(rs.getString("info"));
        return event;
    }
}
