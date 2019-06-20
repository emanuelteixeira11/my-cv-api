package com.api.cv.web.rest;

import com.api.cv.domain.ProfessionalExperience;
import com.api.cv.service.ProfessionalExperienceService;
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
 * REST controller for managing {@link com.api.cv.domain.ProfessionalExperience}.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalExperienceResource.class);

    private static final String ENTITY_NAME = "professionalExperience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfessionalExperienceService professionalExperienceService;

    public ProfessionalExperienceResource(ProfessionalExperienceService professionalExperienceService) {
        this.professionalExperienceService = professionalExperienceService;
    }

    /**
     * {@code POST  /professional-experiences} : Create a new professionalExperience.
     *
     * @param professionalExperience the professionalExperience to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionalExperience, or with status {@code 400 (Bad Request)} if the professionalExperience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professional-experiences")
    public ResponseEntity<ProfessionalExperience> createProfessionalExperience(@Valid @RequestBody ProfessionalExperience professionalExperience) throws URISyntaxException {
        log.debug("REST request to save ProfessionalExperience : {}", professionalExperience);
        if (professionalExperience.getId() != null) {
            throw new BadRequestAlertException("A new professionalExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionalExperience result = professionalExperienceService.save(professionalExperience);
        return ResponseEntity.created(new URI("/api/professional-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professional-experiences} : Updates an existing professionalExperience.
     *
     * @param professionalExperience the professionalExperience to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionalExperience,
     * or with status {@code 400 (Bad Request)} if the professionalExperience is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionalExperience couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professional-experiences")
    public ResponseEntity<ProfessionalExperience> updateProfessionalExperience(@Valid @RequestBody ProfessionalExperience professionalExperience) throws URISyntaxException {
        log.debug("REST request to update ProfessionalExperience : {}", professionalExperience);
        if (professionalExperience.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfessionalExperience result = professionalExperienceService.save(professionalExperience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, professionalExperience.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professional-experiences} : get all the professionalExperiences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professionalExperiences in body.
     */
    @GetMapping("/professional-experiences")
    public ResponseEntity<List<ProfessionalExperience>> getAllProfessionalExperiences(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ProfessionalExperiences");
        Page<ProfessionalExperience> page = professionalExperienceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /professional-experiences/:id} : get the "id" professionalExperience.
     *
     * @param id the id of the professionalExperience to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionalExperience, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professional-experiences/{id}")
    public ResponseEntity<ProfessionalExperience> getProfessionalExperience(@PathVariable Long id) {
        log.debug("REST request to get ProfessionalExperience : {}", id);
        Optional<ProfessionalExperience> professionalExperience = professionalExperienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(professionalExperience);
    }

    /**
     * {@code DELETE  /professional-experiences/:id} : delete the "id" professionalExperience.
     *
     * @param id the id of the professionalExperience to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professional-experiences/{id}")
    public ResponseEntity<Void> deleteProfessionalExperience(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionalExperience : {}", id);
        professionalExperienceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
