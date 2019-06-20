package com.api.cv.service;

import com.api.cv.domain.ProfessionalExperience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ProfessionalExperience}.
 */
public interface ProfessionalExperienceService {

    /**
     * Save a professionalExperience.
     *
     * @param professionalExperience the entity to save.
     * @return the persisted entity.
     */
    ProfessionalExperience save(ProfessionalExperience professionalExperience);

    /**
     * Get all the professionalExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfessionalExperience> findAll(Pageable pageable);


    /**
     * Get the "id" professionalExperience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfessionalExperience> findOne(Long id);

    /**
     * Delete the "id" professionalExperience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
