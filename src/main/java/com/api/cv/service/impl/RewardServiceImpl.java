package com.api.cv.service.impl;

import com.api.cv.service.RewardService;
import com.api.cv.domain.Reward;
import com.api.cv.repository.RewardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reward}.
 */
@Service
@Transactional
public class RewardServiceImpl implements RewardService {

    private final Logger log = LoggerFactory.getLogger(RewardServiceImpl.class);

    private final RewardRepository rewardRepository;

    public RewardServiceImpl(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    /**
     * Save a reward.
     *
     * @param reward the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Reward save(Reward reward) {
        log.debug("Request to save Reward : {}", reward);
        return rewardRepository.save(reward);
    }

    /**
     * Get all the rewards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reward> findAll(Pageable pageable) {
        log.debug("Request to get all Rewards");
        return rewardRepository.findAll(pageable);
    }


    /**
     * Get one reward by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reward> findOne(Long id) {
        log.debug("Request to get Reward : {}", id);
        return rewardRepository.findById(id);
    }

    /**
     * Delete the reward by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reward : {}", id);
        rewardRepository.deleteById(id);
    }
}
