package com.api.cv.service;

import com.api.cv.domain.AcademicExperience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AcademicExperience}.
 */
public interface AcademicExperienceService {

    /**
     * Save a academicExperience.
     *
     * @param academicExperience the entity to save.
     * @return the persisted entity.
     */
    AcademicExperience save(AcademicExperience academicExperience);

    /**
     * Get all the academicExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcademicExperience> findAll(Pageable pageable);


    /**
     * Get the "id" academicExperience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicExperience> findOne(Long id);

    /**
     * Delete the "id" academicExperience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
