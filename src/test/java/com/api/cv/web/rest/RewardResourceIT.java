package com.api.cv.web.rest;

import com.api.cv.MyCvApiApp;
import com.api.cv.domain.Reward;
import com.api.cv.repository.RewardRepository;
import com.api.cv.service.RewardService;
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
 * Integration tests for the {@Link RewardResource} REST controller.
 */
@SpringBootTest(classes = MyCvApiApp.class)
public class RewardResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ISSUER_ENTITY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUER_ENTITY = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardService rewardService;

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

    private MockMvc restRewardMockMvc;

    private Reward reward;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RewardResource rewardResource = new RewardResource(rewardService);
        this.restRewardMockMvc = MockMvcBuilders.standaloneSetup(rewardResource)
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
    public static Reward createEntity(EntityManager em) {
        Reward reward = new Reward()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .issuerEntity(DEFAULT_ISSUER_ENTITY)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION);
        return reward;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reward createUpdatedEntity(EntityManager em) {
        Reward reward = new Reward()
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .issuerEntity(UPDATED_ISSUER_ENTITY)
            .shortDescription(UPDATED_SHORT_DESCRIPTION);
        return reward;
    }

    @BeforeEach
    public void initTest() {
        reward = createEntity(em);
    }

    @Test
    @Transactional
    public void createReward() throws Exception {
        int databaseSizeBeforeCreate = rewardRepository.findAll().size();

        // Create the Reward
        restRewardMockMvc.perform(post("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reward)))
            .andExpect(status().isCreated());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeCreate + 1);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReward.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReward.getIssuerEntity()).isEqualTo(DEFAULT_ISSUER_ENTITY);
        assertThat(testReward.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRewardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rewardRepository.findAll().size();

        // Create the Reward with an existing ID
        reward.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRewardMockMvc.perform(post("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reward)))
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = rewardRepository.findAll().size();
        // set the field null
        reward.setTitle(null);

        // Create the Reward, which fails.

        restRewardMockMvc.perform(post("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reward)))
            .andExpect(status().isBadRequest());

        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = rewardRepository.findAll().size();
        // set the field null
        reward.setDate(null);

        // Create the Reward, which fails.

        restRewardMockMvc.perform(post("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reward)))
            .andExpect(status().isBadRequest());

        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuerEntityIsRequired() throws Exception {
        int databaseSizeBeforeTest = rewardRepository.findAll().size();
        // set the field null
        reward.setIssuerEntity(null);

        // Create the Reward, which fails.

        restRewardMockMvc.perform(post("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reward)))
            .andExpect(status().isBadRequest());

        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = rewardRepository.findAll().size();
        // set the field null
        reward.setShortDescription(null);

        // Create the Reward, which fails.

        restRewardMockMvc.perform(post("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reward)))
            .andExpect(status().isBadRequest());

        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRewards() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get all the rewardList
        restRewardMockMvc.perform(get("/api/rewards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reward.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].issuerEntity").value(hasItem(DEFAULT_ISSUER_ENTITY.toString())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get the reward
        restRewardMockMvc.perform(get("/api/rewards/{id}", reward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reward.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.issuerEntity").value(DEFAULT_ISSUER_ENTITY.toString()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReward() throws Exception {
        // Get the reward
        restRewardMockMvc.perform(get("/api/rewards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReward() throws Exception {
        // Initialize the database
        rewardService.save(reward);

        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Update the reward
        Reward updatedReward = rewardRepository.findById(reward.getId()).get();
        // Disconnect from session so that the updates on updatedReward are not directly saved in db
        em.detach(updatedReward);
        updatedReward
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .issuerEntity(UPDATED_ISSUER_ENTITY)
            .shortDescription(UPDATED_SHORT_DESCRIPTION);

        restRewardMockMvc.perform(put("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReward)))
            .andExpect(status().isOk());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReward.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReward.getIssuerEntity()).isEqualTo(UPDATED_ISSUER_ENTITY);
        assertThat(testReward.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Create the Reward

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardMockMvc.perform(put("/api/rewards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reward)))
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReward() throws Exception {
        // Initialize the database
        rewardService.save(reward);

        int databaseSizeBeforeDelete = rewardRepository.findAll().size();

        // Delete the reward
        restRewardMockMvc.perform(delete("/api/rewards/{id}", reward.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reward.class);
        Reward reward1 = new Reward();
        reward1.setId(1L);
        Reward reward2 = new Reward();
        reward2.setId(reward1.getId());
        assertThat(reward1).isEqualTo(reward2);
        reward2.setId(2L);
        assertThat(reward1).isNotEqualTo(reward2);
        reward1.setId(null);
        assertThat(reward1).isNotEqualTo(reward2);
    }
}
