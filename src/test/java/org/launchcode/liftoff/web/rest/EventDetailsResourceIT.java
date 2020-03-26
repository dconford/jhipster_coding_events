package org.launchcode.liftoff.web.rest;

import org.launchcode.liftoff.JhipsterCodingEventsApp;
import org.launchcode.liftoff.domain.EventDetails;
import org.launchcode.liftoff.repository.EventDetailsRepository;
import org.launchcode.liftoff.service.EventDetailsService;
import org.launchcode.liftoff.service.dto.EventDetailsDTO;
import org.launchcode.liftoff.service.mapper.EventDetailsMapper;
import org.launchcode.liftoff.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.launchcode.liftoff.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EventDetailsResource} REST controller.
 */
@SpringBootTest(classes = JhipsterCodingEventsApp.class)
public class EventDetailsResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private EventDetailsRepository eventDetailsRepository;

    @Autowired
    private EventDetailsMapper eventDetailsMapper;

    @Autowired
    private EventDetailsService eventDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEventDetailsMockMvc;

    private EventDetails eventDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventDetailsResource eventDetailsResource = new EventDetailsResource(eventDetailsService);
        this.restEventDetailsMockMvc = MockMvcBuilders.standaloneSetup(eventDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventDetails createEntity(EntityManager em) {
        EventDetails eventDetails = new EventDetails()
            .description(DEFAULT_DESCRIPTION)
            .email(DEFAULT_EMAIL);
        return eventDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventDetails createUpdatedEntity(EntityManager em) {
        EventDetails eventDetails = new EventDetails()
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL);
        return eventDetails;
    }

    @BeforeEach
    public void initTest() {
        eventDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventDetails() throws Exception {
        int databaseSizeBeforeCreate = eventDetailsRepository.findAll().size();

        // Create the EventDetails
        EventDetailsDTO eventDetailsDTO = eventDetailsMapper.toDto(eventDetails);
        restEventDetailsMockMvc.perform(post("/api/event-details")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the EventDetails in the database
        List<EventDetails> eventDetailsList = eventDetailsRepository.findAll();
        assertThat(eventDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EventDetails testEventDetails = eventDetailsList.get(eventDetailsList.size() - 1);
        assertThat(testEventDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEventDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createEventDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventDetailsRepository.findAll().size();

        // Create the EventDetails with an existing ID
        eventDetails.setId(1L);
        EventDetailsDTO eventDetailsDTO = eventDetailsMapper.toDto(eventDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventDetailsMockMvc.perform(post("/api/event-details")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventDetails in the database
        List<EventDetails> eventDetailsList = eventDetailsRepository.findAll();
        assertThat(eventDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEventDetails() throws Exception {
        // Initialize the database
        eventDetailsRepository.saveAndFlush(eventDetails);

        // Get all the eventDetailsList
        restEventDetailsMockMvc.perform(get("/api/event-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getEventDetails() throws Exception {
        // Initialize the database
        eventDetailsRepository.saveAndFlush(eventDetails);

        // Get the eventDetails
        restEventDetailsMockMvc.perform(get("/api/event-details/{id}", eventDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventDetails.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    public void getNonExistingEventDetails() throws Exception {
        // Get the eventDetails
        restEventDetailsMockMvc.perform(get("/api/event-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventDetails() throws Exception {
        // Initialize the database
        eventDetailsRepository.saveAndFlush(eventDetails);

        int databaseSizeBeforeUpdate = eventDetailsRepository.findAll().size();

        // Update the eventDetails
        EventDetails updatedEventDetails = eventDetailsRepository.findById(eventDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEventDetails are not directly saved in db
        em.detach(updatedEventDetails);
        updatedEventDetails
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL);
        EventDetailsDTO eventDetailsDTO = eventDetailsMapper.toDto(updatedEventDetails);

        restEventDetailsMockMvc.perform(put("/api/event-details")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the EventDetails in the database
        List<EventDetails> eventDetailsList = eventDetailsRepository.findAll();
        assertThat(eventDetailsList).hasSize(databaseSizeBeforeUpdate);
        EventDetails testEventDetails = eventDetailsList.get(eventDetailsList.size() - 1);
        assertThat(testEventDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEventDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingEventDetails() throws Exception {
        int databaseSizeBeforeUpdate = eventDetailsRepository.findAll().size();

        // Create the EventDetails
        EventDetailsDTO eventDetailsDTO = eventDetailsMapper.toDto(eventDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventDetailsMockMvc.perform(put("/api/event-details")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventDetails in the database
        List<EventDetails> eventDetailsList = eventDetailsRepository.findAll();
        assertThat(eventDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventDetails() throws Exception {
        // Initialize the database
        eventDetailsRepository.saveAndFlush(eventDetails);

        int databaseSizeBeforeDelete = eventDetailsRepository.findAll().size();

        // Delete the eventDetails
        restEventDetailsMockMvc.perform(delete("/api/event-details/{id}", eventDetails.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventDetails> eventDetailsList = eventDetailsRepository.findAll();
        assertThat(eventDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
