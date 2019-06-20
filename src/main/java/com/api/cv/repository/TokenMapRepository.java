package com.api.cv.repository;

import com.api.cv.domain.TokenMap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TokenMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TokenMapRepository extends JpaRepository<TokenMap, Long> {

}
