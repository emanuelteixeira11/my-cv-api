package com.api.cv.web.rest;

import com.api.cv.MyCvApiApp;
import com.api.cv.domain.Technology;
import com.api.cv.repository.TechnologyRepository;
import com.api.cv.service.TechnologyService;
import com.api.cv.web.rest.errors.ExceptionTranslator;

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

import static com.api.cv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TechnologyResource} REST controller.
 */
@SpringBootTest(classes = MyCvApiApp.class)
public class TechnologyResourceIT {

    private static final String DEFAULT_TECH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TECH_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_FRAMEWORK = false;
    private static final Boolean UPDATED_IS_FRAMEWORK = true;

    private static final Double DEFAULT_EXPERIENCE_RATE = 1D;
    private static final Double UPDATED_EXPERIENCE_RATE = 2D;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyService technologyService;

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

    private MockMvc restTechnologyMockMvc;

    private Technology technology;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechnologyResource technologyResource = new TechnologyResource(technologyService);
        this.restTechnologyMockMvc = MockMvcBuilders.standaloneSetup(technologyResource)
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
    public static Technology createEntity(EntityManager em) {
        Technology technology = new Technology()
            .techName(DEFAULT_TECH_NAME)
            .isFramework(DEFAULT_IS_FRAMEWORK)
            .experienceRate(DEFAULT_EXPERIENCE_RATE);
        return technology;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Technology createUpdatedEntity(EntityManager em) {
        Technology technology = new Technology()
            .techName(UPDATED_TECH_NAME)
            .isFramework(UPDATED_IS_FRAMEWORK)
            .experienceRate(UPDATED_EXPERIENCE_RATE);
        return technology;
    }

    @BeforeEach
    public void initTest() {
        technology = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnology() throws Exception {
        int databaseSizeBeforeCreate = technologyRepository.findAll().size();

        // Create the Technology
        restTechnologyMockMvc.perform(post("/api/technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isCreated());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeCreate + 1);
        Technology testTechnology = technologyList.get(technologyList.size() - 1);
        assertThat(testTechnology.getTechName()).isEqualTo(DEFAULT_TECH_NAME);
        assertThat(testTechnology.isIsFramework()).isEqualTo(DEFAULT_IS_FRAMEWORK);
        assertThat(testTechnology.getExperienceRate()).isEqualTo(DEFAULT_EXPERIENCE_RATE);
    }

    @Test
    @Transactional
    public void createTechnologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = technologyRepository.findAll().size();

        // Create the Technology with an existing ID
        technology.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnologyMockMvc.perform(post("/api/technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTechNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = technologyRepository.findAll().size();
        // set the field null
        technology.setTechName(null);

        // Create the Technology, which fails.

        restTechnologyMockMvc.perform(post("/api/technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isBadRequest());

        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsFrameworkIsRequired() throws Exception {
        int databaseSizeBeforeTest = technologyRepository.findAll().size();
        // set the field null
        technology.setIsFramework(null);

        // Create the Technology, which fails.

        restTechnologyMockMvc.perform(post("/api/technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isBadRequest());

        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExperienceRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = technologyRepository.findAll().size();
        // set the field null
        technology.setExperienceRate(null);

        // Create the Technology, which fails.

        restTechnologyMockMvc.perform(post("/api/technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isBadRequest());

        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTechnologies() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get all the technologyList
        restTechnologyMockMvc.perform(get("/api/technologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technology.getId().intValue())))
            .andExpect(jsonPath("$.[*].techName").value(hasItem(DEFAULT_TECH_NAME.toString())))
            .andExpect(jsonPath("$.[*].isFramework").value(hasItem(DEFAULT_IS_FRAMEWORK.booleanValue())))
            .andExpect(jsonPath("$.[*].experienceRate").value(hasItem(DEFAULT_EXPERIENCE_RATE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", technology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(technology.getId().intValue()))
            .andExpect(jsonPath("$.techName").value(DEFAULT_TECH_NAME.toString()))
            .andExpect(jsonPath("$.isFramework").value(DEFAULT_IS_FRAMEWORK.booleanValue()))
            .andExpect(jsonPath("$.experienceRate").value(DEFAULT_EXPERIENCE_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTechnology() throws Exception {
        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnology() throws Exception {
        // Initialize the database
        technologyService.save(technology);

        int databaseSizeBeforeUpdate = technologyRepository.findAll().size();

        // Update the technology
        Technology updatedTechnology = technologyRepository.findById(technology.getId()).get();
        // Disconnect from session so that the updates on updatedTechnology are not directly saved in db
        em.detach(updatedTechnology);
        updatedTechnology
            .techName(UPDATED_TECH_NAME)
            .isFramework(UPDATED_IS_FRAMEWORK)
            .experienceRate(UPDATED_EXPERIENCE_RATE);

        restTechnologyMockMvc.perform(put("/api/technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTechnology)))
            .andExpect(status().isOk());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeUpdate);
        Technology testTechnology = technologyList.get(technologyList.size() - 1);
        assertThat(testTechnology.getTechName()).isEqualTo(UPDATED_TECH_NAME);
        assertThat(testTechnology.isIsFramework()).isEqualTo(UPDATED_IS_FRAMEWORK);
        assertThat(testTechnology.getExperienceRate()).isEqualTo(UPDATED_EXPERIENCE_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTechnology() throws Exception {
        int databaseSizeBeforeUpdate = technologyRepository.findAll().size();

        // Create the Technology

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnologyMockMvc.perform(put("/api/technologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechnology() throws Exception {
        // Initialize the database
        technologyService.save(technology);

        int databaseSizeBeforeDelete = technologyRepository.findAll().size();

        // Delete the technology
        restTechnologyMockMvc.perform(delete("/api/technologies/{id}", technology.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Technology.class);
        Technology technology1 = new Technology();
        technology1.setId(1L);
        Technology technology2 = new Technology();
        technology2.setId(technology1.getId());
        assertThat(technology1).isEqualTo(technology2);
        technology2.setId(2L);
        assertThat(technology1).isNotEqualTo(technology2);
        technology1.setId(null);
        assertThat(technology1).isNotEqualTo(technology2);
    }
}
