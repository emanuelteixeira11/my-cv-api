package com.api.cv.service;

import com.api.cv.domain.Technology;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Technology}.
 */
public interface TechnologyService {

    /**
     * Save a technology.
     *
     * @param technology the entity to save.
     * @return the persisted entity.
     */
    Technology save(Technology technology);

    /**
     * Get all the technologies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Technology> findAll(Pageable pageable);


    /**
     * Get the "id" technology.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Technology> findOne(Long id);

    /**
     * Delete the "id" technology.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
