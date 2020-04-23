package com.isoft.tms.service.impl;

import com.isoft.tms.service.TrainingClassService;
import com.isoft.tms.domain.TrainingClass;
import com.isoft.tms.repository.TrainingClassRepository;
import com.isoft.tms.service.dto.TrainingClassDTO;
import com.isoft.tms.service.mapper.TrainingClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TrainingClass}.
 */
@Service
@Transactional
public class TrainingClassServiceImpl implements TrainingClassService {

    private final Logger log = LoggerFactory.getLogger(TrainingClassServiceImpl.class);

    private final TrainingClassRepository trainingClassRepository;

    private final TrainingClassMapper trainingClassMapper;

    public TrainingClassServiceImpl(TrainingClassRepository trainingClassRepository, TrainingClassMapper trainingClassMapper) {
        this.trainingClassRepository = trainingClassRepository;
        this.trainingClassMapper = trainingClassMapper;
    }

    /**
     * Save a trainingClass.
     *
     * @param trainingClassDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TrainingClassDTO save(TrainingClassDTO trainingClassDTO) {
        log.debug("Request to save TrainingClass : {}", trainingClassDTO);
        TrainingClass trainingClass = trainingClassMapper.toEntity(trainingClassDTO);
        trainingClass = trainingClassRepository.save(trainingClass);
        return trainingClassMapper.toDto(trainingClass);
    }

    /**
     * Get all the trainingClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TrainingClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainingClasses");
        return trainingClassRepository.findAll(pageable)
            .map(trainingClassMapper::toDto);
    }

    /**
     * Get one trainingClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingClassDTO> findOne(Long id) {
        log.debug("Request to get TrainingClass : {}", id);
        return trainingClassRepository.findById(id)
            .map(trainingClassMapper::toDto);
    }

    /**
     * Delete the trainingClass by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainingClass : {}", id);
        trainingClassRepository.deleteById(id);
    }
}
