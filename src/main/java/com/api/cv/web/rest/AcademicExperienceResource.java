package com.api.cv.web.rest;

import com.api.cv.domain.AcademicExperience;
import com.api.cv.service.AcademicExperienceService;
import com.api.cv.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.api.cv.domain.AcademicExperience}.
 */
@RestController
@RequestMapping("/api")
public class AcademicExperienceResource {

    private final Logger log = LoggerFactory.getLogger(AcademicExperienceResource.class);

    private static final String ENTITY_NAME = "academicExperience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicExperienceService academicExperienceService;

    public AcademicExperienceResource(AcademicExperienceService academicExperienceService) {
        this.academicExperienceService = academicExperienceService;
    }

    /**
     * {@code POST  /academic-experiences} : Create a new academicExperience.
     *
     * @param academicExperience the academicExperience to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicExperience, or with status {@code 400 (Bad Request)} if the academicExperience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-experiences")
    public ResponseEntity<AcademicExperience> createAcademicExperience(@Valid @RequestBody AcademicExperience academicExperience) throws URISyntaxException {
        log.debug("REST request to save AcademicExperience : {}", academicExperience);
        if (academicExperience.getId() != null) {
            throw new BadRequestAlertException("A new academicExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicExperience result = academicExperienceService.save(academicExperience);
        return ResponseEntity.created(new URI("/api/academic-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-experiences} : Updates an existing academicExperience.
     *
     * @param academicExperience the academicExperience to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicExperience,
     * or with status {@code 400 (Bad Request)} if the academicExperience is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicExperience couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-experiences")
    public ResponseEntity<AcademicExperience> updateAcademicExperience(@Valid @RequestBody AcademicExperience academicExperience) throws URISyntaxException {
        log.debug("REST request to update AcademicExperience : {}", academicExperience);
        if (academicExperience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcademicExperience result = academicExperienceService.save(academicExperience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicExperience.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /academic-experiences} : get all the academicExperiences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicExperiences in body.
     */
    @GetMapping("/academic-experiences")
    public ResponseEntity<List<AcademicExperience>> getAllAcademicExperiences(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AcademicExperiences");
        Page<AcademicExperience> page = academicExperienceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /academic-experiences/:id} : get the "id" academicExperience.
     *
     * @param id the id of the academicExperience to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicExperience, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-experiences/{id}")
    public ResponseEntity<AcademicExperience> getAcademicExperience(@PathVariable Long id) {
        log.debug("REST request to get AcademicExperience : {}", id);
        Optional<AcademicExperience> academicExperience = academicExperienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicExperience);
    }

    /**
     * {@code DELETE  /academic-experiences/:id} : delete the "id" academicExperience.
     *
     * @param id the id of the academicExperience to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-experiences/{id}")
    public ResponseEntity<Void> deleteAcademicExperience(@PathVariable Long id) {
        log.debug("REST request to delete AcademicExperience : {}", id);
        academicExperienceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
