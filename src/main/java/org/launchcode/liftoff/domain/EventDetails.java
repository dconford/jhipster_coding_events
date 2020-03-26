package org.launchcode.liftoff.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EventDetails.
 */
@Entity
@Table(name = "event_details")
public class EventDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "eventDetails")
    // @JsonIgnore
    private Event event;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public EventDetails description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public EventDetails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Event getEvent() {
        return event;
    }

    public EventDetails event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDetails)) {
            return false;
        }
        return id != null && id.equals(((EventDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventDetails{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
