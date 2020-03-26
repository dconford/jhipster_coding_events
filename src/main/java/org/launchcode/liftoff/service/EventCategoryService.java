package org.launchcode.liftoff.service;

import org.launchcode.liftoff.service.dto.EventCategoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link org.launchcode.liftoff.domain.EventCategory}.
 */
public interface EventCategoryService {

    /**
     * Save a eventCategory.
     *
     * @param eventCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    EventCategoryDTO save(EventCategoryDTO eventCategoryDTO);

    /**
     * Get all the eventCategories.
     *
     * @return the list of entities.
     */
    List<EventCategoryDTO> findAll();

    /**
     * Get the "id" eventCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" eventCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
