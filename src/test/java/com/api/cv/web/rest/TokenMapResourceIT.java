package com.api.cv.web.rest;

import com.api.cv.MyCvApiApp;
import com.api.cv.domain.TokenMap;
import com.api.cv.repository.TokenMapRepository;
import com.api.cv.service.TokenMapService;
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
 * Integration tests for the {@Link TokenMapResource} REST controller.
 */
@SpringBootTest(classes = MyCvApiApp.class)
public class TokenMapResourceIT {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TokenMapRepository tokenMapRepository;

    @Autowired
    private TokenMapService tokenMapService;

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

    private MockMvc restTokenMapMockMvc;

    private TokenMap tokenMap;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TokenMapResource tokenMapResource = new TokenMapResource(tokenMapService);
        this.restTokenMapMockMvc = MockMvcBuilders.standaloneSetup(tokenMapResource)
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
    public static TokenMap createEntity(EntityManager em) {
        TokenMap tokenMap = new TokenMap()
            .token(DEFAULT_TOKEN)
            .entityName(DEFAULT_ENTITY_NAME)
            .validTo(DEFAULT_VALID_TO);
        return tokenMap;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TokenMap createUpdatedEntity(EntityManager em) {
        TokenMap tokenMap = new TokenMap()
            .token(UPDATED_TOKEN)
            .entityName(UPDATED_ENTITY_NAME)
            .validTo(UPDATED_VALID_TO);
        return tokenMap;
    }

    @BeforeEach
    public void initTest() {
        tokenMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createTokenMap() throws Exception {
        int databaseSizeBeforeCreate = tokenMapRepository.findAll().size();

        // Create the TokenMap
        restTokenMapMockMvc.perform(post("/api/token-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenMap)))
            .andExpect(status().isCreated());

        // Validate the TokenMap in the database
        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeCreate + 1);
        TokenMap testTokenMap = tokenMapList.get(tokenMapList.size() - 1);
        assertThat(testTokenMap.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testTokenMap.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testTokenMap.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createTokenMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tokenMapRepository.findAll().size();

        // Create the TokenMap with an existing ID
        tokenMap.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTokenMapMockMvc.perform(post("/api/token-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenMap)))
            .andExpect(status().isBadRequest());

        // Validate the TokenMap in the database
        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = tokenMapRepository.findAll().size();
        // set the field null
        tokenMap.setToken(null);

        // Create the TokenMap, which fails.

        restTokenMapMockMvc.perform(post("/api/token-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenMap)))
            .andExpect(status().isBadRequest());

        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tokenMapRepository.findAll().size();
        // set the field null
        tokenMap.setEntityName(null);

        // Create the TokenMap, which fails.

        restTokenMapMockMvc.perform(post("/api/token-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenMap)))
            .andExpect(status().isBadRequest());

        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = tokenMapRepository.findAll().size();
        // set the field null
        tokenMap.setValidTo(null);

        // Create the TokenMap, which fails.

        restTokenMapMockMvc.perform(post("/api/token-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenMap)))
            .andExpect(status().isBadRequest());

        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTokenMaps() throws Exception {
        // Initialize the database
        tokenMapRepository.saveAndFlush(tokenMap);

        // Get all the tokenMapList
        restTokenMapMockMvc.perform(get("/api/token-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tokenMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getTokenMap() throws Exception {
        // Initialize the database
        tokenMapRepository.saveAndFlush(tokenMap);

        // Get the tokenMap
        restTokenMapMockMvc.perform(get("/api/token-maps/{id}", tokenMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tokenMap.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTokenMap() throws Exception {
        // Get the tokenMap
        restTokenMapMockMvc.perform(get("/api/token-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTokenMap() throws Exception {
        // Initialize the database
        tokenMapService.save(tokenMap);

        int databaseSizeBeforeUpdate = tokenMapRepository.findAll().size();

        // Update the tokenMap
        TokenMap updatedTokenMap = tokenMapRepository.findById(tokenMap.getId()).get();
        // Disconnect from session so that the updates on updatedTokenMap are not directly saved in db
        em.detach(updatedTokenMap);
        updatedTokenMap
            .token(UPDATED_TOKEN)
            .entityName(UPDATED_ENTITY_NAME)
            .validTo(UPDATED_VALID_TO);

        restTokenMapMockMvc.perform(put("/api/token-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTokenMap)))
            .andExpect(status().isOk());

        // Validate the TokenMap in the database
        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeUpdate);
        TokenMap testTokenMap = tokenMapList.get(tokenMapList.size() - 1);
        assertThat(testTokenMap.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testTokenMap.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testTokenMap.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingTokenMap() throws Exception {
        int databaseSizeBeforeUpdate = tokenMapRepository.findAll().size();

        // Create the TokenMap

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenMapMockMvc.perform(put("/api/token-maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tokenMap)))
            .andExpect(status().isBadRequest());

        // Validate the TokenMap in the database
        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTokenMap() throws Exception {
        // Initialize the database
        tokenMapService.save(tokenMap);

        int databaseSizeBeforeDelete = tokenMapRepository.findAll().size();

        // Delete the tokenMap
        restTokenMapMockMvc.perform(delete("/api/token-maps/{id}", tokenMap.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TokenMap> tokenMapList = tokenMapRepository.findAll();
        assertThat(tokenMapList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TokenMap.class);
        TokenMap tokenMap1 = new TokenMap();
        tokenMap1.setId(1L);
        TokenMap tokenMap2 = new TokenMap();
        tokenMap2.setId(tokenMap1.getId());
        assertThat(tokenMap1).isEqualTo(tokenMap2);
        tokenMap2.setId(2L);
        assertThat(tokenMap1).isNotEqualTo(tokenMap2);
        tokenMap1.setId(null);
        assertThat(tokenMap1).isNotEqualTo(tokenMap2);
    }
}
