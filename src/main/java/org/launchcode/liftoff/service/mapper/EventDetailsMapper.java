package org.launchcode.liftoff.service.mapper;


import org.launchcode.liftoff.domain.*;
import org.launchcode.liftoff.service.dto.EventDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventDetails} and its DTO {@link EventDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventDetailsMapper extends EntityMapper<EventDetailsDTO, EventDetails> {


    @Mapping(target = "event", ignore = true)
    EventDetails toEntity(EventDetailsDTO eventDetailsDTO);

    default EventDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventDetails eventDetails = new EventDetails();
        eventDetails.setId(id);
        return eventDetails;
    }
}
