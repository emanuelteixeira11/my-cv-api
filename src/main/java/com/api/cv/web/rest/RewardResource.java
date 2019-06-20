package com.api.cv.web.rest;

import com.api.cv.domain.Reward;
import com.api.cv.service.RewardService;
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
 * REST controller for managing {@link com.api.cv.domain.Reward}.
 */
@RestController
@RequestMapping("/api")
public class RewardResource {

    private final Logger log = LoggerFactory.getLogger(RewardResource.class);

    private static final String ENTITY_NAME = "reward";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RewardService rewardService;

    public RewardResource(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * {@code POST  /rewards} : Create a new reward.
     *
     * @param reward the reward to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reward, or with status {@code 400 (Bad Request)} if the reward has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rewards")
    public ResponseEntity<Reward> createReward(@Valid @RequestBody Reward reward) throws URISyntaxException {
        log.debug("REST request to save Reward : {}", reward);
        if (reward.getId() != null) {
            throw new BadRequestAlertException("A new reward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reward result = rewardService.save(reward);
        return ResponseEntity.created(new URI("/api/rewards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rewards} : Updates an existing reward.
     *
     * @param reward the reward to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reward,
     * or with status {@code 400 (Bad Request)} if the reward is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reward couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rewards")
    public ResponseEntity<Reward> updateReward(@Valid @RequestBody Reward reward) throws URISyntaxException {
        log.debug("REST request to update Reward : {}", reward);
        if (reward.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reward result = rewardService.save(reward);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reward.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rewards} : get all the rewards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rewards in body.
     */
    @GetMapping("/rewards")
    public ResponseEntity<List<Reward>> getAllRewards(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Rewards");
        Page<Reward> page = rewardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rewards/:id} : get the "id" reward.
     *
     * @param id the id of the reward to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reward, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rewards/{id}")
    public ResponseEntity<Reward> getReward(@PathVariable Long id) {
        log.debug("REST request to get Reward : {}", id);
        Optional<Reward> reward = rewardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reward);
    }

    /**
     * {@code DELETE  /rewards/:id} : delete the "id" reward.
     *
     * @param id the id of the reward to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rewards/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable Long id) {
        log.debug("REST request to delete Reward : {}", id);
        rewardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
