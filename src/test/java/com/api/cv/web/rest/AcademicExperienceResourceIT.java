package com.api.cv.web.rest;

import com.api.cv.MyCvApiApp;
import com.api.cv.domain.AcademicExperience;
import com.api.cv.repository.AcademicExperienceRepository;
import com.api.cv.service.AcademicExperienceService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.api.cv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AcademicExperienceResource} REST controller.
 */
@SpringBootTest(classes = MyCvApiApp.class)
public class AcademicExperienceResourceIT {

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CLASSIFICATION = 1D;
    private static final Double UPDATED_CLASSIFICATION = 2D;

    @Autowired
    private AcademicExperienceRepository academicExperienceRepository;

    @Autowired
    private AcademicExperienceService academicExperienceService;

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

    private MockMvc restAcademicExperienceMockMvc;

    private AcademicExperience academicExperience;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcademicExperienceResource academicExperienceResource = new AcademicExperienceResource(academicExperienceService);
        this.restAcademicExperienceMockMvc = MockMvcBuilders.standaloneSetup(academicExperienceResource)
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
    public static AcademicExperience createEntity(EntityManager em) {
        AcademicExperience academicExperience = new AcademicExperience()
            .school(DEFAULT_SCHOOL)
            .degree(DEFAULT_DEGREE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .classification(DEFAULT_CLASSIFICATION);
        return academicExperience;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicExperience createUpdatedEntity(EntityManager em) {
        AcademicExperience academicExperience = new AcademicExperience()
            .school(UPDATED_SCHOOL)
            .degree(UPDATED_DEGREE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .classification(UPDATED_CLASSIFICATION);
        return academicExperience;
    }

    @BeforeEach
    public void initTest() {
        academicExperience = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicExperience() throws Exception {
        int databaseSizeBeforeCreate = academicExperienceRepository.findAll().size();

        // Create the AcademicExperience
        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicExperience)))
            .andExpect(status().isCreated());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicExperience testAcademicExperience = academicExperienceList.get(academicExperienceList.size() - 1);
        assertThat(testAcademicExperience.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testAcademicExperience.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testAcademicExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAcademicExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testAcademicExperience.getClassification()).isEqualTo(DEFAULT_CLASSIFICATION);
    }

    @Test
    @Transactional
    public void createAcademicExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicExperienceRepository.findAll().size();

        // Create the AcademicExperience with an existing ID
        academicExperience.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicExperience)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSchoolIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicExperienceRepository.findAll().size();
        // set the field null
        academicExperience.setSchool(null);

        // Create the AcademicExperience, which fails.

        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicExperience)))
            .andExpect(status().isBadRequest());

        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicExperienceRepository.findAll().size();
        // set the field null
        academicExperience.setDegree(null);

        // Create the AcademicExperience, which fails.

        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicExperience)))
            .andExpect(status().isBadRequest());

        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicExperienceRepository.findAll().size();
        // set the field null
        academicExperience.setStartDate(null);

        // Create the AcademicExperience, which fails.

        restAcademicExperienceMockMvc.perform(post("/api/academic-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicExperience)))
            .andExpect(status().isBadRequest());

        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcademicExperiences() throws Exception {
        // Initialize the database
        academicExperienceRepository.saveAndFlush(academicExperience);

        // Get all the academicExperienceList
        restAcademicExperienceMockMvc.perform(get("/api/academic-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAcademicExperience() throws Exception {
        // Initialize the database
        academicExperienceRepository.saveAndFlush(academicExperience);

        // Get the academicExperience
        restAcademicExperienceMockMvc.perform(get("/api/academic-experiences/{id}", academicExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(academicExperience.getId().intValue()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.toString()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.classification").value(DEFAULT_CLASSIFICATION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAcademicExperience() throws Exception {
        // Get the academicExperience
        restAcademicExperienceMockMvc.perform(get("/api/academic-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicExperience() throws Exception {
        // Initialize the database
        academicExperienceService.save(academicExperience);

        int databaseSizeBeforeUpdate = academicExperienceRepository.findAll().size();

        // Update the academicExperience
        AcademicExperience updatedAcademicExperience = academicExperienceRepository.findById(academicExperience.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicExperience are not directly saved in db
        em.detach(updatedAcademicExperience);
        updatedAcademicExperience
            .school(UPDATED_SCHOOL)
            .degree(UPDATED_DEGREE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .classification(UPDATED_CLASSIFICATION);

        restAcademicExperienceMockMvc.perform(put("/api/academic-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcademicExperience)))
            .andExpect(status().isOk());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeUpdate);
        AcademicExperience testAcademicExperience = academicExperienceList.get(academicExperienceList.size() - 1);
        assertThat(testAcademicExperience.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testAcademicExperience.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testAcademicExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAcademicExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAcademicExperience.getClassification()).isEqualTo(UPDATED_CLASSIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicExperience() throws Exception {
        int databaseSizeBeforeUpdate = academicExperienceRepository.findAll().size();

        // Create the AcademicExperience

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicExperienceMockMvc.perform(put("/api/academic-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicExperience)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicExperience in the database
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcademicExperience() throws Exception {
        // Initialize the database
        academicExperienceService.save(academicExperience);

        int databaseSizeBeforeDelete = academicExperienceRepository.findAll().size();

        // Delete the academicExperience
        restAcademicExperienceMockMvc.perform(delete("/api/academic-experiences/{id}", academicExperience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AcademicExperience> academicExperienceList = academicExperienceRepository.findAll();
        assertThat(academicExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicExperience.class);
        AcademicExperience academicExperience1 = new AcademicExperience();
        academicExperience1.setId(1L);
        AcademicExperience academicExperience2 = new AcademicExperience();
        academicExperience2.setId(academicExperience1.getId());
        assertThat(academicExperience1).isEqualTo(academicExperience2);
        academicExperience2.setId(2L);
        assertThat(academicExperience1).isNotEqualTo(academicExperience2);
        academicExperience1.setId(null);
        assertThat(academicExperience1).isNotEqualTo(academicExperience2);
    }
}
