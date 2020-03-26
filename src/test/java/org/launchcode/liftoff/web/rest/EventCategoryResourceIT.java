package org.launchcode.liftoff.web.rest;

import org.launchcode.liftoff.JhipsterCodingEventsApp;
import org.launchcode.liftoff.domain.EventCategory;
import org.launchcode.liftoff.repository.EventCategoryRepository;
import org.launchcode.liftoff.service.EventCategoryService;
import org.launchcode.liftoff.service.dto.EventCategoryDTO;
import org.launchcode.liftoff.service.mapper.EventCategoryMapper;
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
 * Integration tests for the {@link EventCategoryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterCodingEventsApp.class)
public class EventCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private EventCategoryMapper eventCategoryMapper;

    @Autowired
    private EventCategoryService eventCategoryService;

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

    private MockMvc restEventCategoryMockMvc;

    private EventCategory eventCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventCategoryResource eventCategoryResource = new EventCategoryResource(eventCategoryService);
        this.restEventCategoryMockMvc = MockMvcBuilders.standaloneSetup(eventCategoryResource)
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
    public static EventCategory createEntity(EntityManager em) {
        EventCategory eventCategory = new EventCategory()
            .name(DEFAULT_NAME);
        return eventCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventCategory createUpdatedEntity(EntityManager em) {
        EventCategory eventCategory = new EventCategory()
            .name(UPDATED_NAME);
        return eventCategory;
    }

    @BeforeEach
    public void initTest() {
        eventCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventCategory() throws Exception {
        int databaseSizeBeforeCreate = eventCategoryRepository.findAll().size();

        // Create the EventCategory
        EventCategoryDTO eventCategoryDTO = eventCategoryMapper.toDto(eventCategory);
        restEventCategoryMockMvc.perform(post("/api/event-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the EventCategory in the database
        List<EventCategory> eventCategoryList = eventCategoryRepository.findAll();
        assertThat(eventCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        EventCategory testEventCategory = eventCategoryList.get(eventCategoryList.size() - 1);
        assertThat(testEventCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEventCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventCategoryRepository.findAll().size();

        // Create the EventCategory with an existing ID
        eventCategory.setId(1L);
        EventCategoryDTO eventCategoryDTO = eventCategoryMapper.toDto(eventCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventCategoryMockMvc.perform(post("/api/event-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventCategory in the database
        List<EventCategory> eventCategoryList = eventCategoryRepository.findAll();
        assertThat(eventCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventCategoryRepository.findAll().size();
        // set the field null
        eventCategory.setName(null);

        // Create the EventCategory, which fails.
        EventCategoryDTO eventCategoryDTO = eventCategoryMapper.toDto(eventCategory);

        restEventCategoryMockMvc.perform(post("/api/event-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<EventCategory> eventCategoryList = eventCategoryRepository.findAll();
        assertThat(eventCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventCategories() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);

        // Get all the eventCategoryList
        restEventCategoryMockMvc.perform(get("/api/event-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getEventCategory() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);

        // Get the eventCategory
        restEventCategoryMockMvc.perform(get("/api/event-categories/{id}", eventCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingEventCategory() throws Exception {
        // Get the eventCategory
        restEventCategoryMockMvc.perform(get("/api/event-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventCategory() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);

        int databaseSizeBeforeUpdate = eventCategoryRepository.findAll().size();

        // Update the eventCategory
        EventCategory updatedEventCategory = eventCategoryRepository.findById(eventCategory.getId()).get();
        // Disconnect from session so that the updates on updatedEventCategory are not directly saved in db
        em.detach(updatedEventCategory);
        updatedEventCategory
            .name(UPDATED_NAME);
        EventCategoryDTO eventCategoryDTO = eventCategoryMapper.toDto(updatedEventCategory);

        restEventCategoryMockMvc.perform(put("/api/event-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the EventCategory in the database
        List<EventCategory> eventCategoryList = eventCategoryRepository.findAll();
        assertThat(eventCategoryList).hasSize(databaseSizeBeforeUpdate);
        EventCategory testEventCategory = eventCategoryList.get(eventCategoryList.size() - 1);
        assertThat(testEventCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEventCategory() throws Exception {
        int databaseSizeBeforeUpdate = eventCategoryRepository.findAll().size();

        // Create the EventCategory
        EventCategoryDTO eventCategoryDTO = eventCategoryMapper.toDto(eventCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventCategoryMockMvc.perform(put("/api/event-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eventCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventCategory in the database
        List<EventCategory> eventCategoryList = eventCategoryRepository.findAll();
        assertThat(eventCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventCategory() throws Exception {
        // Initialize the database
        eventCategoryRepository.saveAndFlush(eventCategory);

        int databaseSizeBeforeDelete = eventCategoryRepository.findAll().size();

        // Delete the eventCategory
        restEventCategoryMockMvc.perform(delete("/api/event-categories/{id}", eventCategory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventCategory> eventCategoryList = eventCategoryRepository.findAll();
        assertThat(eventCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
