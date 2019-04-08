
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * An utility class to help with database
 */
public class DaoUtil {
    private static final String CONNECTION_DB = "jdbc:h2:./eventresultdb";
    
    /**
     * 
     * @return returns Connection to database
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException{      
        try {
            return DriverManager.getConnection(CONNECTION_DB);
        } catch (SQLException e) {
            throw new SQLException("Couldn't connect to the database", e);
        }
    }
    
 /** Sets values to preparedStatement. Arbitary number of Object parameters
  * can be passed to the method.
     * @param preparedStatement preparedStatement where passed values are set to
     * @param values any number of objects to be set to the given
     * preparedStatement as values.
     * @throws java.sql.SQLException 
 */
    public static void setValues
        (PreparedStatement preparedStatement, Object... values) throws SQLException{
        for (int i = 0; i < values.length; i++){
            preparedStatement.setObject(i + 1, values[i]);
        }        
    }
    
    /**
     * Initializes tables if not existing.
     */
    public static void initialize() {        
        String createEvent = "CREATE TABLE IF NOT EXISTS Event("
                + "id INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(64),"
                + "location VARCHAR(64),"
                + "date VARCHAR(64),"
                + "info VARCHAR(64))";
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(createEvent);) {  
            stmt.executeUpdate();
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        
        String createContest = "CREATE TABLE IF NOT EXISTS Contest("
                + "id INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(64),"
                + "startingTime VARCHAR(64),"
                + "numberOfParticipants INTEGER)";
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(createContest);) {  
            stmt.executeUpdate();
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        
        String createParticipant = "CREATE TABLE IF NOT EXISTS Participant("
                + "id INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + "bidNumber INTEGER,"
                + "firstName VARCHAR(64),"
                + "lastName VARCHAR(64),"
                + "eMail VARCHAR(64),"
                + "phone VARCHAR(64),"
                + "address VARCHAR(64),"
                + "club VARCHAR(64),"
                + "raceResult VARCHAR(64),"
                + "contestId INTEGER,"
                + "foreign key (contestId) references Contest(id))";
        try (Connection conn = DaoUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(createParticipant);) {  
            stmt.executeUpdate();
        } catch(SQLException ex){
            System.out.println(ex.getMessage()); 
        }
        
    }
    
        
}
