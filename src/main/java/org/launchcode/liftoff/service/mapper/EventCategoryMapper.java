package org.launchcode.liftoff.service.mapper;


import org.launchcode.liftoff.domain.*;
import org.launchcode.liftoff.service.dto.EventCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventCategory} and its DTO {@link EventCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventCategoryMapper extends EntityMapper<EventCategoryDTO, EventCategory> {


    @Mapping(target = "events", ignore = true)
    @Mapping(target = "removeEvent", ignore = true)
    EventCategory toEntity(EventCategoryDTO eventCategoryDTO);

    default EventCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventCategory eventCategory = new EventCategory();
        eventCategory.setId(id);
        return eventCategory;
    }
}
