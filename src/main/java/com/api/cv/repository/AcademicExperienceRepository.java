package com.api.cv.repository;

import com.api.cv.domain.AcademicExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AcademicExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicExperienceRepository extends JpaRepository<AcademicExperience, Long> {

}
