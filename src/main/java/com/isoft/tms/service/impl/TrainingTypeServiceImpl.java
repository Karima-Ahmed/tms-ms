package com.isoft.tms.service.impl;

import com.isoft.tms.service.TrainingTypeService;
import com.isoft.tms.domain.TrainingType;
import com.isoft.tms.repository.TrainingTypeRepository;
import com.isoft.tms.service.dto.TrainingTypeDTO;
import com.isoft.tms.service.mapper.TrainingTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TrainingType}.
 */
@Service
@Transactional
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final Logger log = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    private final TrainingTypeRepository trainingTypeRepository;

    private final TrainingTypeMapper trainingTypeMapper;

    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    /**
     * Save a trainingType.
     *
     * @param trainingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrainingTypeDTO save(TrainingTypeDTO trainingTypeDTO) {
        log.debug("Request to save TrainingType : {}", trainingTypeDTO);
        TrainingType trainingType = trainingTypeMapper.toEntity(trainingTypeDTO);
        trainingType = trainingTypeRepository.save(trainingType);
        return trainingTypeMapper.toDto(trainingType);
    }

    /**
     * Get all the trainingTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrainingTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainingTypes");
        return trainingTypeRepository.findAll(pageable)
            .map(trainingTypeMapper::toDto);
    }

    /**
     * Get one trainingType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingTypeDTO> findOne(Long id) {
        log.debug("Request to get TrainingType : {}", id);
        return trainingTypeRepository.findById(id)
            .map(trainingTypeMapper::toDto);
    }

    /**
     * Delete the trainingType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainingType : {}", id);
        trainingTypeRepository.deleteById(id);
    }
}
