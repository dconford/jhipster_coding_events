package org.launchcode.liftoff.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EventCategory.
 */
@Entity
@Table(name = "event_category")
public class EventCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "eventCategory")
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EventCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public EventCategory events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public EventCategory addEvent(Event event) {
        this.events.add(event);
        event.setEventCategory(this);
        return this;
    }

    public EventCategory removeEvent(Event event) {
        this.events.remove(event);
        event.setEventCategory(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventCategory)) {
            return false;
        }
        return id != null && id.equals(((EventCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return getName();
    }

//    @Override
//    public String toString() {
//        return "EventCategory{" +
//            "id=" + getId() +
//            ", name='" + getName() + "'" +
//            "}";
//    }
}
