package com.isoft.tms.repository;

import com.isoft.tms.domain.TrainingType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrainingType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long>, JpaSpecificationExecutor<TrainingType> {
}
