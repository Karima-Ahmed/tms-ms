package com.isoft.tms.service;

import com.isoft.tms.service.dto.TrainingClassDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.TrainingClass}.
 */
public interface TrainingClassService {

    /**
     * Save a trainingClass.
     *
     * @param trainingClassDTO the entity to save.
     * @return the persisted entity.
     */
    TrainingClassDTO save(TrainingClassDTO trainingClassDTO);

    /**
     * Get all the trainingClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingClassDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainingClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainingClassDTO> findOne(Long id);

    /**
     * Delete the "id" trainingClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
