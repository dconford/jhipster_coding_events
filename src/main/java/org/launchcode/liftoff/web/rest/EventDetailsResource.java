package org.launchcode.liftoff.web.rest;

import org.launchcode.liftoff.service.EventDetailsService;
import org.launchcode.liftoff.web.rest.errors.BadRequestAlertException;
import org.launchcode.liftoff.service.dto.EventDetailsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link org.launchcode.liftoff.domain.EventDetails}.
 */
@RestController
@RequestMapping("/api")
public class EventDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EventDetailsResource.class);

    private static final String ENTITY_NAME = "eventDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventDetailsService eventDetailsService;

    public EventDetailsResource(EventDetailsService eventDetailsService) {
        this.eventDetailsService = eventDetailsService;
    }

    /**
     * {@code POST  /event-details} : Create a new eventDetails.
     *
     * @param eventDetailsDTO the eventDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventDetailsDTO, or with status {@code 400 (Bad Request)} if the eventDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-details")
    public ResponseEntity<EventDetailsDTO> createEventDetails(@Valid @RequestBody EventDetailsDTO eventDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save EventDetails : {}", eventDetailsDTO);
        if (eventDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventDetailsDTO result = eventDetailsService.save(eventDetailsDTO);
        return ResponseEntity.created(new URI("/api/event-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-details} : Updates an existing eventDetails.
     *
     * @param eventDetailsDTO the eventDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the eventDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-details")
    public ResponseEntity<EventDetailsDTO> updateEventDetails(@Valid @RequestBody EventDetailsDTO eventDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update EventDetails : {}", eventDetailsDTO);
        if (eventDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventDetailsDTO result = eventDetailsService.save(eventDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-details} : get all the eventDetails.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventDetails in body.
     */
    @GetMapping("/event-details")
    public List<EventDetailsDTO> getAllEventDetails(@RequestParam(required = false) String filter) {
        if ("event-is-null".equals(filter)) {
            log.debug("REST request to get all EventDetailss where event is null");
            return eventDetailsService.findAllWhereEventIsNull();
        }
        log.debug("REST request to get all EventDetails");
        return eventDetailsService.findAll();
    }

    /**
     * {@code GET  /event-details/:id} : get the "id" eventDetails.
     *
     * @param id the id of the eventDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-details/{id}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable Long id) {
        log.debug("REST request to get EventDetails : {}", id);
        Optional<EventDetailsDTO> eventDetailsDTO = eventDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventDetailsDTO);
    }

    /**
     * {@code DELETE  /event-details/:id} : delete the "id" eventDetails.
     *
     * @param id the id of the eventDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-details/{id}")
    public ResponseEntity<Void> deleteEventDetails(@PathVariable Long id) {
        log.debug("REST request to delete EventDetails : {}", id);
        eventDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
