package com.api.cv.service;

import com.api.cv.domain.Reward;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Reward}.
 */
public interface RewardService {

    /**
     * Save a reward.
     *
     * @param reward the entity to save.
     * @return the persisted entity.
     */
    Reward save(Reward reward);

    /**
     * Get all the rewards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reward> findAll(Pageable pageable);


    /**
     * Get the "id" reward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reward> findOne(Long id);

    /**
     * Delete the "id" reward.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
