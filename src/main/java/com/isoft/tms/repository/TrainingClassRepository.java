package com.isoft.tms.repository;

import com.isoft.tms.domain.TrainingClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long>, JpaSpecificationExecutor<TrainingClass> {
}
