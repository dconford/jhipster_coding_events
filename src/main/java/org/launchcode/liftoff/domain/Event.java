package org.launchcode.liftoff.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private EventDetails eventDetails;

    @ManyToOne
   // @JsonIgnoreProperties("events")
    private EventCategory eventCategory;

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

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public Event eventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
        return this;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public Event eventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
        return this;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return getName();
    }
}

