/**
 * An utility class to help and clean up DAO-classes
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olavi
 */
public class DaoUtil {
    private static final String CONNECTION_DB = "jdbc:h2:./eventresultdb";
    
    /**
     * 
     * @return returns Connection to database
     * @throws SQLException 
     */
    public static Connection getConnection(){      
        try {
            return DriverManager.getConnection(CONNECTION_DB);
        } catch (SQLException ex) {
            Logger.getLogger(DaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
        
}
