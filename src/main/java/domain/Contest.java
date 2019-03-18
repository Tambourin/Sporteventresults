
package domain;

import java.time.LocalTime;

/**
 *
 * @author Olavi
 */
public class Contest {
    private Integer id;
    private String name;
    private LocalTime startingTime;    

    public Contest() {
        this.name = "unnamed contest";
    }

    public Contest(Integer id, String name, LocalTime contestStarts) {
        this.id = id;
        this.name = name;
        this.startingTime = contestStarts;       
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }



    @Override
    public String toString() {
        return name + " " + startingTime.toString();
    }
    
    
    
}
