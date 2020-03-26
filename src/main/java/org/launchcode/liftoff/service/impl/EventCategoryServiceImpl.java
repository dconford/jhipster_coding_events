package org.launchcode.liftoff.service.impl;

import org.launchcode.liftoff.service.EventCategoryService;
import org.launchcode.liftoff.domain.EventCategory;
import org.launchcode.liftoff.repository.EventCategoryRepository;
import org.launchcode.liftoff.service.dto.EventCategoryDTO;
import org.launchcode.liftoff.service.mapper.EventCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EventCategory}.
 */
@Service
@Transactional
public class EventCategoryServiceImpl implements EventCategoryService {

    private final Logger log = LoggerFactory.getLogger(EventCategoryServiceImpl.class);

    private final EventCategoryRepository eventCategoryRepository;

    private final EventCategoryMapper eventCategoryMapper;

    public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepository, EventCategoryMapper eventCategoryMapper) {
        this.eventCategoryRepository = eventCategoryRepository;
        this.eventCategoryMapper = eventCategoryMapper;
    }

    /**
     * Save a eventCategory.
     *
     * @param eventCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventCategoryDTO save(EventCategoryDTO eventCategoryDTO) {
        log.debug("Request to save EventCategory : {}", eventCategoryDTO);
        EventCategory eventCategory = eventCategoryMapper.toEntity(eventCategoryDTO);
        eventCategory = eventCategoryRepository.save(eventCategory);
        return eventCategoryMapper.toDto(eventCategory);
    }

    /**
     * Get all the eventCategories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventCategoryDTO> findAll() {
        log.debug("Request to get all EventCategories");
        return eventCategoryRepository.findAll().stream()
            .map(eventCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eventCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventCategoryDTO> findOne(Long id) {
        log.debug("Request to get EventCategory : {}", id);
        return eventCategoryRepository.findById(id)
            .map(eventCategoryMapper::toDto);
    }

    /**
     * Delete the eventCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventCategory : {}", id);
        eventCategoryRepository.deleteById(id);
    }
}
