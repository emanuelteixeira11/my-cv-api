package com.api.cv.service;

import com.api.cv.domain.TokenMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TokenMap}.
 */
public interface TokenMapService {

    /**
     * Save a tokenMap.
     *
     * @param tokenMap the entity to save.
     * @return the persisted entity.
     */
    TokenMap save(TokenMap tokenMap);

    /**
     * Get all the tokenMaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TokenMap> findAll(Pageable pageable);


    /**
     * Get the "id" tokenMap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TokenMap> findOne(Long id);

    /**
     * Delete the "id" tokenMap.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
