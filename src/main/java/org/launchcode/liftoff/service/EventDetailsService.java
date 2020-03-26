package org.launchcode.liftoff.service;

import org.launchcode.liftoff.service.dto.EventDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.launchcode.liftoff.domain.EventDetails}.
 */
public interface EventDetailsService {

    /**
     * Save a eventDetails.
     *
     * @param eventDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    EventDetailsDTO save(EventDetailsDTO eventDetailsDTO);

    /**
     * Get all the eventDetails.
     *
     * @return the list of entities.
     */
    List<EventDetailsDTO> findAll();
    /**
     * Get all the EventDetailsDTO where Event is {@code null}.
     *
     * @return the list of entities.
     */
    List<EventDetailsDTO> findAllWhereEventIsNull();

    /**
     * Get the "id" eventDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" eventDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
