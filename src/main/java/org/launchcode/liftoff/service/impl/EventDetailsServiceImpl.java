package org.launchcode.liftoff.service.impl;

import org.launchcode.liftoff.service.EventDetailsService;
import org.launchcode.liftoff.domain.EventDetails;
import org.launchcode.liftoff.repository.EventDetailsRepository;
import org.launchcode.liftoff.service.dto.EventDetailsDTO;
import org.launchcode.liftoff.service.mapper.EventDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link EventDetails}.
 */
@Service
@Transactional
public class EventDetailsServiceImpl implements EventDetailsService {

    private final Logger log = LoggerFactory.getLogger(EventDetailsServiceImpl.class);

    private final EventDetailsRepository eventDetailsRepository;

    private final EventDetailsMapper eventDetailsMapper;

    public EventDetailsServiceImpl(EventDetailsRepository eventDetailsRepository, EventDetailsMapper eventDetailsMapper) {
        this.eventDetailsRepository = eventDetailsRepository;
        this.eventDetailsMapper = eventDetailsMapper;
    }

    /**
     * Save a eventDetails.
     *
     * @param eventDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventDetailsDTO save(EventDetailsDTO eventDetailsDTO) {
        log.debug("Request to save EventDetails : {}", eventDetailsDTO);
        EventDetails eventDetails = eventDetailsMapper.toEntity(eventDetailsDTO);
        eventDetails = eventDetailsRepository.save(eventDetails);
        return eventDetailsMapper.toDto(eventDetails);
    }

    /**
     * Get all the eventDetails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventDetailsDTO> findAll() {
        log.debug("Request to get all EventDetails");
        return eventDetailsRepository.findAll().stream()
            .map(eventDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the eventDetails where Event is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<EventDetailsDTO> findAllWhereEventIsNull() {
        log.debug("Request to get all eventDetails where Event is null");
        return StreamSupport
            .stream(eventDetailsRepository.findAll().spliterator(), false)
            .filter(eventDetails -> eventDetails.getEvent() == null)
            .map(eventDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eventDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventDetailsDTO> findOne(Long id) {
        log.debug("Request to get EventDetails : {}", id);
        return eventDetailsRepository.findById(id)
            .map(eventDetailsMapper::toDto);
    }

    /**
     * Delete the eventDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventDetails : {}", id);
        eventDetailsRepository.deleteById(id);
    }
}
