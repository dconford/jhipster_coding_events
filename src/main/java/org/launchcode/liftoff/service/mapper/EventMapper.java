package org.launchcode.liftoff.service.mapper;


import org.launchcode.liftoff.domain.*;
import org.launchcode.liftoff.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventDetailsMapper.class, EventCategoryMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "eventDetails.id", target = "eventDetailsId")
    @Mapping(source = "eventCategory.id", target = "eventCategoryId")
    EventDTO toDto(Event event);

    @Mapping(source = "eventDetailsId", target = "eventDetails")
    @Mapping(source = "eventCategoryId", target = "eventCategory")
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
