package org.launchcode.liftoff.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.launchcode.liftoff.domain.EventDetails} entity.
 */
public class EventDetailsDTO implements Serializable {

    private Long id;

    @Size(max = 500)
    private String description;

    private String email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDetailsDTO eventDetailsDTO = (EventDetailsDTO) o;
        if (eventDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventDetailsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
