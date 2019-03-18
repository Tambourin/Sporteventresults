
package domain;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 *
 * @author Olavi
 */
public class Event {
    private Integer id;
    private String name;
    private String location;
    private LocalDate eventDate;
    private LocalTime eventStart;
    private LocalTime eventEnds;
    private String info;

    public Event() {
        this.name = "unnamed";
        this.location = "location not set";
        this.info = "no extra info";
    }

    public Event(Integer id, String name, String location, LocalDate eventDate, LocalTime eventStart, LocalTime eventEnds, String info) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.eventDate = eventDate;
        this.eventStart = eventStart;
        this.eventEnds = eventEnds;
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalTime getEventStart() {
        return eventStart;
    }

    public LocalTime getEventEnds() {
        return eventEnds;
    }

    public String getInfo() {
        return info;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventStart(LocalTime eventStart) {
        this.eventStart = eventStart;
    }

    public void setEventEnds(LocalTime eventEnds) {
        this.eventEnds = eventEnds;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
    
}
