package org.launchcode.liftoff.web.rest;

import org.launchcode.liftoff.service.EventCategoryService;
import org.launchcode.liftoff.web.rest.errors.BadRequestAlertException;
import org.launchcode.liftoff.service.dto.EventCategoryDTO;

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

/**
 * REST controller for managing {@link org.launchcode.liftoff.domain.EventCategory}.
 */
@RestController
@RequestMapping("/api")
public class EventCategoryResource {

    private final Logger log = LoggerFactory.getLogger(EventCategoryResource.class);

    private static final String ENTITY_NAME = "eventCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventCategoryService eventCategoryService;

    public EventCategoryResource(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    /**
     * {@code POST  /event-categories} : Create a new eventCategory.
     *
     * @param eventCategoryDTO the eventCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventCategoryDTO, or with status {@code 400 (Bad Request)} if the eventCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-categories")
    public ResponseEntity<EventCategoryDTO> createEventCategory(@Valid @RequestBody EventCategoryDTO eventCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save EventCategory : {}", eventCategoryDTO);
        if (eventCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventCategoryDTO result = eventCategoryService.save(eventCategoryDTO);
        return ResponseEntity.created(new URI("/api/event-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-categories} : Updates an existing eventCategory.
     *
     * @param eventCategoryDTO the eventCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the eventCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-categories")
    public ResponseEntity<EventCategoryDTO> updateEventCategory(@Valid @RequestBody EventCategoryDTO eventCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update EventCategory : {}", eventCategoryDTO);
        if (eventCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventCategoryDTO result = eventCategoryService.save(eventCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-categories} : get all the eventCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventCategories in body.
     */
    @GetMapping("/event-categories")
    public List<EventCategoryDTO> getAllEventCategories() {
        log.debug("REST request to get all EventCategories");
        return eventCategoryService.findAll();
    }

    /**
     * {@code GET  /event-categories/:id} : get the "id" eventCategory.
     *
     * @param id the id of the eventCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-categories/{id}")
    public ResponseEntity<EventCategoryDTO> getEventCategory(@PathVariable Long id) {
        log.debug("REST request to get EventCategory : {}", id);
        Optional<EventCategoryDTO> eventCategoryDTO = eventCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventCategoryDTO);
    }

    /**
     * {@code DELETE  /event-categories/:id} : delete the "id" eventCategory.
     *
     * @param id the id of the eventCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-categories/{id}")
    public ResponseEntity<Void> deleteEventCategory(@PathVariable Long id) {
        log.debug("REST request to delete EventCategory : {}", id);
        eventCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
