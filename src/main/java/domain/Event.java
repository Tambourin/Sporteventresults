
package domain;

import java.time.LocalDate;


/**
 * Event. All contests are part of an event.
 */
public class Event {
    private Integer id;
    private String name;
    private String location;
    private LocalDate date;
    private String info;

    public Event() {
        this.name = "unnamed";
        this.location = "location not set";
        this.info = "no extra info";
    }

    public Event(Integer id, String name, String location, LocalDate date, String info) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.info = info;
    }

    public Event(String name, String location, LocalDate date, String info) {
        this.name = name;
        this.location = location;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
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

    public void setDate(LocalDate eventDate) {
        this.date = eventDate;
    }    

    public void setInfo(String info) {
        this.info = info;
    }
    
    @Override
    public String toString() {
        return this.name + ", " + this.date;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Event e = (Event) obj;
        return e.id.equals(this.id);
    }
}
