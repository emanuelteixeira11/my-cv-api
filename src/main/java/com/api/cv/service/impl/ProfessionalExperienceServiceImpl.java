package com.api.cv.service.impl;

import com.api.cv.service.ProfessionalExperienceService;
import com.api.cv.domain.ProfessionalExperience;
import com.api.cv.repository.ProfessionalExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProfessionalExperience}.
 */
@Service
@Transactional
public class ProfessionalExperienceServiceImpl implements ProfessionalExperienceService {

    private final Logger log = LoggerFactory.getLogger(ProfessionalExperienceServiceImpl.class);

    private final ProfessionalExperienceRepository professionalExperienceRepository;

    public ProfessionalExperienceServiceImpl(ProfessionalExperienceRepository professionalExperienceRepository) {
        this.professionalExperienceRepository = professionalExperienceRepository;
    }

    /**
     * Save a professionalExperience.
     *
     * @param professionalExperience the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProfessionalExperience save(ProfessionalExperience professionalExperience) {
        log.debug("Request to save ProfessionalExperience : {}", professionalExperience);
        return professionalExperienceRepository.save(professionalExperience);
    }

    /**
     * Get all the professionalExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfessionalExperience> findAll(Pageable pageable) {
        log.debug("Request to get all ProfessionalExperiences");
        return professionalExperienceRepository.findAll(pageable);
    }


    /**
     * Get one professionalExperience by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionalExperience> findOne(Long id) {
        log.debug("Request to get ProfessionalExperience : {}", id);
        return professionalExperienceRepository.findById(id);
    }

    /**
     * Delete the professionalExperience by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfessionalExperience : {}", id);
        professionalExperienceRepository.deleteById(id);
    }
}
