
package dao;

import domain.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Dao class responsible for database operations related to event entity 
 * @author Olavi
 */
public class EventDaoJdbc {
    
    /**
     * Create a record to database
     * @param event 
     */
    public void create(Event event) {
        String sqlQuery = "INSERT INTO Event(name, date, location, info) "
                + "VALUES(?, ?, ?, ?)";
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            String[] values = {
                event.getName(), event.getDate().toString(),
                event.getLocation(), event.getInfo()};
            DaoUtil.setValues(stmt, values);
            stmt.executeUpdate();
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
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
            event = prepareEvent(rs);
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        return event;
    }
    
    private Event prepareEvent(ResultSet rs) throws SQLException {
        if (!rs.next()) {
                return null;
        }
        Event event = new Event(rs.getInt("id"), 
                rs.getString("name"),
                rs.getString("location"), 
                LocalDate.parse(rs.getString("date")), 
                rs.getString("info"));
        return event;
    }
}
