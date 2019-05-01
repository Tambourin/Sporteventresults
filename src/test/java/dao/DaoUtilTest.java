/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olavi
 */
public class DaoUtilTest {
    Connection conn;
    
    public DaoUtilTest() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetConnection() throws Exception {
        conn = DaoUtil.getConnection();
        assertTrue(conn != null);
        conn.close();
    }

    @Test
    public void testSetValues() throws Exception {
        
    }

    @Test
    public void testInitialize() {
        String sqlQuery = "SELECT COUNT(*) AS numberOf FROM \"INFORMATION_SCHEMA\".TABLES WHERE TABLE_TYPE = 'TABLE'";
        int result = 0;
        
        DaoUtil.initialize();
        
        try (Connection conn = DaoUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            DaoUtil.setValues(stmt);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                return;
            }
            System.out.println(rs.getInt("numberOf"));
            result = rs.getInt("numberOf");
        } catch (SQLException ex){
            ex.printStackTrace(); 
        }
        
        assertEquals(3, result);
    }
    
}
