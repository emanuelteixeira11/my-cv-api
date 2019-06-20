package com.api.cv.service.impl;

import com.api.cv.service.AcademicExperienceService;
import com.api.cv.domain.AcademicExperience;
import com.api.cv.repository.AcademicExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AcademicExperience}.
 */
@Service
@Transactional
public class AcademicExperienceServiceImpl implements AcademicExperienceService {

    private final Logger log = LoggerFactory.getLogger(AcademicExperienceServiceImpl.class);

    private final AcademicExperienceRepository academicExperienceRepository;

    public AcademicExperienceServiceImpl(AcademicExperienceRepository academicExperienceRepository) {
        this.academicExperienceRepository = academicExperienceRepository;
    }

    /**
     * Save a academicExperience.
     *
     * @param academicExperience the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AcademicExperience save(AcademicExperience academicExperience) {
        log.debug("Request to save AcademicExperience : {}", academicExperience);
        return academicExperienceRepository.save(academicExperience);
    }

    /**
     * Get all the academicExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AcademicExperience> findAll(Pageable pageable) {
        log.debug("Request to get all AcademicExperiences");
        return academicExperienceRepository.findAll(pageable);
    }


    /**
     * Get one academicExperience by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicExperience> findOne(Long id) {
        log.debug("Request to get AcademicExperience : {}", id);
        return academicExperienceRepository.findById(id);
    }

    /**
     * Delete the academicExperience by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicExperience : {}", id);
        academicExperienceRepository.deleteById(id);
    }
}
