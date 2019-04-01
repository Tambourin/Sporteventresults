/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olavi
 */
public class ContestTest {
    Contest instance;
    
    public ContestTest() {
    }
    
    @Before
    public void setUp() {
        instance = new Contest(1, "Hölkkä", LocalTime.of(20, 30));
    }


    @Test
    public void testEmptyConstructor() {        
        assertEquals("unnamed contest", new Contest().getName());
    }
    
    /**
     * Test of getId method, of class Contest.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");        
        Integer expResult = 1;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Contest.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");        
        String expResult = "Hölkkä";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStartingTime method, of class Contest.
     */
    @Test
    public void testGetStartingTime() {
        System.out.println("getStartingTime");        
        LocalTime expResult = LocalTime.of(20, 30);
        LocalTime result = instance.getStartingTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Contest.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");   
        instance.setId(2);
        Integer expResult = 2;
        assertEquals(expResult, instance.getId());
    }

    /**
     * Test of setName method, of class Contest.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Helga";
        String expResult = "Helga";
        instance.setName(name);
        assertEquals(expResult, instance.getName());
    }

    /**
     * Test of setStartingTime method, of class Contest.
     */
    @Test
    public void testSetStartingTime() {
        System.out.println("setStartingTime");
        LocalTime startingTime = LocalTime.of(7, 20);
        LocalTime expResult = LocalTime.of(7, 20);
        instance.setStartingTime(startingTime);
        assertEquals(expResult, instance.getStartingTime());
    }

    @Test
    public void testSetNullStartingTime() {
        System.out.println("setStartingTime");
        LocalTime startingTime = null;
        LocalTime expResult = null;
        instance.setStartingTime(startingTime);
        assertEquals(expResult, instance.getStartingTime());
    }


    /**
     * Test of toString method, of class Contest.
     */
    @Test
    public void testToString() {
        System.out.println("toString");       
        String expResult = "Hölkkä";
        String result = instance.toString();
        assertEquals(expResult, result);

    }
    
}
