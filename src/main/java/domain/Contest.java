
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
    private Event event;

    public Contest() {
        this.name = "unnamed contest";
    }
    
public Contest(Integer id, String name, LocalTime contestStarts, Event event) {
        this.id = id;
        this.name = name;
        this.startingTime = contestStarts; 
        this.event = event;
    }

    public Contest(String name, LocalTime contestStarts, Event event) {
        this.name = name;
        this.startingTime = contestStarts; 
        this.event = event;
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
    
    public void setEvent(Event event) {
        this.event = event;
    }
    
    public Event getEvent() {
        return this.event;
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
        return Objects.equals(p.id, this.id);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    
    
}
