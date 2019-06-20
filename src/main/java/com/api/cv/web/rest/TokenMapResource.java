package com.api.cv.web.rest;

import com.api.cv.domain.TokenMap;
import com.api.cv.service.TokenMapService;
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
 * REST controller for managing {@link com.api.cv.domain.TokenMap}.
 */
@RestController
@RequestMapping("/api")
public class TokenMapResource {

    private final Logger log = LoggerFactory.getLogger(TokenMapResource.class);

    private static final String ENTITY_NAME = "tokenMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TokenMapService tokenMapService;

    public TokenMapResource(TokenMapService tokenMapService) {
        this.tokenMapService = tokenMapService;
    }

    /**
     * {@code POST  /token-maps} : Create a new tokenMap.
     *
     * @param tokenMap the tokenMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tokenMap, or with status {@code 400 (Bad Request)} if the tokenMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/token-maps")
    public ResponseEntity<TokenMap> createTokenMap(@Valid @RequestBody TokenMap tokenMap) throws URISyntaxException {
        log.debug("REST request to save TokenMap : {}", tokenMap);
        if (tokenMap.getId() != null) {
            throw new BadRequestAlertException("A new tokenMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TokenMap result = tokenMapService.save(tokenMap);
        return ResponseEntity.created(new URI("/api/token-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /token-maps} : Updates an existing tokenMap.
     *
     * @param tokenMap the tokenMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tokenMap,
     * or with status {@code 400 (Bad Request)} if the tokenMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tokenMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/token-maps")
    public ResponseEntity<TokenMap> updateTokenMap(@Valid @RequestBody TokenMap tokenMap) throws URISyntaxException {
        log.debug("REST request to update TokenMap : {}", tokenMap);
        if (tokenMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TokenMap result = tokenMapService.save(tokenMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tokenMap.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /token-maps} : get all the tokenMaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tokenMaps in body.
     */
    @GetMapping("/token-maps")
    public ResponseEntity<List<TokenMap>> getAllTokenMaps(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TokenMaps");
        Page<TokenMap> page = tokenMapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /token-maps/:id} : get the "id" tokenMap.
     *
     * @param id the id of the tokenMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tokenMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/token-maps/{id}")
    public ResponseEntity<TokenMap> getTokenMap(@PathVariable Long id) {
        log.debug("REST request to get TokenMap : {}", id);
        Optional<TokenMap> tokenMap = tokenMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tokenMap);
    }

    /**
     * {@code DELETE  /token-maps/:id} : delete the "id" tokenMap.
     *
     * @param id the id of the tokenMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/token-maps/{id}")
    public ResponseEntity<Void> deleteTokenMap(@PathVariable Long id) {
        log.debug("REST request to delete TokenMap : {}", id);
        tokenMapService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
