package com.isoft.tms.service;

import com.isoft.tms.service.dto.TrainingTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.TrainingType}.
 */
public interface TrainingTypeService {

    /**
     * Save a trainingType.
     *
     * @param trainingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    TrainingTypeDTO save(TrainingTypeDTO trainingTypeDTO);

    /**
     * Get all the trainingTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainingTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainingType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainingTypeDTO> findOne(Long id);

    /**
     * Delete the "id" trainingType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
