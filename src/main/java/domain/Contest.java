
package domain;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Contest in an event. All participants have a contest.
 * @author Olavi
 */
public class Contest {
    private Integer id;
    private String name;
    private LocalTime startingTime;     
    private Integer numberOfParticipants;

    public Contest() {
        this.name = "unnamed contest";
    }
    
public Contest(Integer id, String name, LocalTime contestStarts) {
        this.id = id;
        this.name = name;
        this.startingTime = contestStarts;       
    }

    public Contest(String name, LocalTime contestStarts) {
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

    public Integer getParticipantsNumber() {
        return numberOfParticipants;
    }

    public void setParticipantsNumber(Integer participantsNumber) {
        this.numberOfParticipants = participantsNumber;
    }
    
    
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        if (obj == this) {
            return true;
        }  
        Contest p = (Contest) obj;
        return p.id == this.id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    
    
}
