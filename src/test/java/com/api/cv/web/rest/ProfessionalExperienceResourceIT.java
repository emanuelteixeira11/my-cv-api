package com.api.cv.web.rest;

import com.api.cv.MyCvApiApp;
import com.api.cv.domain.ProfessionalExperience;
import com.api.cv.repository.ProfessionalExperienceRepository;
import com.api.cv.service.ProfessionalExperienceService;
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
 * Integration tests for the {@Link ProfessionalExperienceResource} REST controller.
 */
@SpringBootTest(classes = MyCvApiApp.class)
public class ProfessionalExperienceResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProfessionalExperienceRepository professionalExperienceRepository;

    @Autowired
    private ProfessionalExperienceService professionalExperienceService;

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

    private MockMvc restProfessionalExperienceMockMvc;

    private ProfessionalExperience professionalExperience;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionalExperienceResource professionalExperienceResource = new ProfessionalExperienceResource(professionalExperienceService);
        this.restProfessionalExperienceMockMvc = MockMvcBuilders.standaloneSetup(professionalExperienceResource)
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
    public static ProfessionalExperience createEntity(EntityManager em) {
        ProfessionalExperience professionalExperience = new ProfessionalExperience()
            .title(DEFAULT_TITLE)
            .company(DEFAULT_COMPANY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return professionalExperience;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfessionalExperience createUpdatedEntity(EntityManager em) {
        ProfessionalExperience professionalExperience = new ProfessionalExperience()
            .title(UPDATED_TITLE)
            .company(UPDATED_COMPANY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return professionalExperience;
    }

    @BeforeEach
    public void initTest() {
        professionalExperience = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessionalExperience() throws Exception {
        int databaseSizeBeforeCreate = professionalExperienceRepository.findAll().size();

        // Create the ProfessionalExperience
        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperience)))
            .andExpect(status().isCreated());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        ProfessionalExperience testProfessionalExperience = professionalExperienceList.get(professionalExperienceList.size() - 1);
        assertThat(testProfessionalExperience.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProfessionalExperience.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testProfessionalExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProfessionalExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createProfessionalExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionalExperienceRepository.findAll().size();

        // Create the ProfessionalExperience with an existing ID
        professionalExperience.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperience)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalExperienceRepository.findAll().size();
        // set the field null
        professionalExperience.setTitle(null);

        // Create the ProfessionalExperience, which fails.

        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperience)))
            .andExpect(status().isBadRequest());

        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalExperienceRepository.findAll().size();
        // set the field null
        professionalExperience.setCompany(null);

        // Create the ProfessionalExperience, which fails.

        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperience)))
            .andExpect(status().isBadRequest());

        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = professionalExperienceRepository.findAll().size();
        // set the field null
        professionalExperience.setStartDate(null);

        // Create the ProfessionalExperience, which fails.

        restProfessionalExperienceMockMvc.perform(post("/api/professional-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperience)))
            .andExpect(status().isBadRequest());

        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfessionalExperiences() throws Exception {
        // Initialize the database
        professionalExperienceRepository.saveAndFlush(professionalExperience);

        // Get all the professionalExperienceList
        restProfessionalExperienceMockMvc.perform(get("/api/professional-experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionalExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProfessionalExperience() throws Exception {
        // Initialize the database
        professionalExperienceRepository.saveAndFlush(professionalExperience);

        // Get the professionalExperience
        restProfessionalExperienceMockMvc.perform(get("/api/professional-experiences/{id}", professionalExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professionalExperience.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessionalExperience() throws Exception {
        // Get the professionalExperience
        restProfessionalExperienceMockMvc.perform(get("/api/professional-experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionalExperience() throws Exception {
        // Initialize the database
        professionalExperienceService.save(professionalExperience);

        int databaseSizeBeforeUpdate = professionalExperienceRepository.findAll().size();

        // Update the professionalExperience
        ProfessionalExperience updatedProfessionalExperience = professionalExperienceRepository.findById(professionalExperience.getId()).get();
        // Disconnect from session so that the updates on updatedProfessionalExperience are not directly saved in db
        em.detach(updatedProfessionalExperience);
        updatedProfessionalExperience
            .title(UPDATED_TITLE)
            .company(UPDATED_COMPANY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restProfessionalExperienceMockMvc.perform(put("/api/professional-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfessionalExperience)))
            .andExpect(status().isOk());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeUpdate);
        ProfessionalExperience testProfessionalExperience = professionalExperienceList.get(professionalExperienceList.size() - 1);
        assertThat(testProfessionalExperience.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProfessionalExperience.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testProfessionalExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProfessionalExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessionalExperience() throws Exception {
        int databaseSizeBeforeUpdate = professionalExperienceRepository.findAll().size();

        // Create the ProfessionalExperience

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessionalExperienceMockMvc.perform(put("/api/professional-experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalExperience)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalExperience in the database
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfessionalExperience() throws Exception {
        // Initialize the database
        professionalExperienceService.save(professionalExperience);

        int databaseSizeBeforeDelete = professionalExperienceRepository.findAll().size();

        // Delete the professionalExperience
        restProfessionalExperienceMockMvc.perform(delete("/api/professional-experiences/{id}", professionalExperience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProfessionalExperience> professionalExperienceList = professionalExperienceRepository.findAll();
        assertThat(professionalExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalExperience.class);
        ProfessionalExperience professionalExperience1 = new ProfessionalExperience();
        professionalExperience1.setId(1L);
        ProfessionalExperience professionalExperience2 = new ProfessionalExperience();
        professionalExperience2.setId(professionalExperience1.getId());
        assertThat(professionalExperience1).isEqualTo(professionalExperience2);
        professionalExperience2.setId(2L);
        assertThat(professionalExperience1).isNotEqualTo(professionalExperience2);
        professionalExperience1.setId(null);
        assertThat(professionalExperience1).isNotEqualTo(professionalExperience2);
    }
}
