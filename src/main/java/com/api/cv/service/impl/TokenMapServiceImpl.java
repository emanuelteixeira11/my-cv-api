package com.api.cv.service.impl;

import com.api.cv.service.TokenMapService;
import com.api.cv.domain.TokenMap;
import com.api.cv.repository.TokenMapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TokenMap}.
 */
@Service
@Transactional
public class TokenMapServiceImpl implements TokenMapService {

    private final Logger log = LoggerFactory.getLogger(TokenMapServiceImpl.class);

    private final TokenMapRepository tokenMapRepository;

    public TokenMapServiceImpl(TokenMapRepository tokenMapRepository) {
        this.tokenMapRepository = tokenMapRepository;
    }

    /**
     * Save a tokenMap.
     *
     * @param tokenMap the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TokenMap save(TokenMap tokenMap) {
        log.debug("Request to save TokenMap : {}", tokenMap);
        return tokenMapRepository.save(tokenMap);
    }

    /**
     * Get all the tokenMaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TokenMap> findAll(Pageable pageable) {
        log.debug("Request to get all TokenMaps");
        return tokenMapRepository.findAll(pageable);
    }


    /**
     * Get one tokenMap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TokenMap> findOne(Long id) {
        log.debug("Request to get TokenMap : {}", id);
        return tokenMapRepository.findById(id);
    }

    /**
     * Delete the tokenMap by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TokenMap : {}", id);
        tokenMapRepository.deleteById(id);
    }
}
