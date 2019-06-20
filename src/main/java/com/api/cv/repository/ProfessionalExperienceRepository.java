package com.api.cv.repository;

import com.api.cv.domain.ProfessionalExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProfessionalExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionalExperienceRepository extends JpaRepository<ProfessionalExperience, Long> {

}
