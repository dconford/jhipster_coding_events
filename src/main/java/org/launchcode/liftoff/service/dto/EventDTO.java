package org.launchcode.liftoff.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.launchcode.liftoff.domain.Event} entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;


    private Long eventDetailsId;

    private Long eventCategoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEventDetailsId() {
        return eventDetailsId;
    }

    public void setEventDetailsId(Long eventDetailsId) {
        this.eventDetailsId = eventDetailsId;
    }

    public Long getEventCategoryId() {
        return eventCategoryId;
    }

    public void setEventCategoryId(Long eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (eventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", eventDetailsId=" + getEventDetailsId() +
            ", eventCategoryId=" + getEventCategoryId() +
            "}";
    }
}
