package com.isoft.tms.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.isoft.tms.domain.TrainingType;
import com.isoft.tms.domain.*; // for static metamodels
import com.isoft.tms.repository.TrainingTypeRepository;
import com.isoft.tms.service.dto.TrainingTypeCriteria;
import com.isoft.tms.service.dto.TrainingTypeDTO;
import com.isoft.tms.service.mapper.TrainingTypeMapper;

/**
 * Service for executing complex queries for {@link TrainingType} entities in the database.
 * The main input is a {@link TrainingTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainingTypeDTO} or a {@link Page} of {@link TrainingTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingTypeQueryService extends QueryService<TrainingType> {

    private final Logger log = LoggerFactory.getLogger(TrainingTypeQueryService.class);

    private final TrainingTypeRepository trainingTypeRepository;

    private final TrainingTypeMapper trainingTypeMapper;

    public TrainingTypeQueryService(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
    }

    /**
     * Return a {@link List} of {@link TrainingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingTypeDTO> findByCriteria(TrainingTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TrainingType> specification = createSpecification(criteria);
        return trainingTypeMapper.toDto(trainingTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainingTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingTypeDTO> findByCriteria(TrainingTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrainingType> specification = createSpecification(criteria);
        return trainingTypeRepository.findAll(specification, page)
            .map(trainingTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TrainingType> specification = createSpecification(criteria);
        return trainingTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TrainingType> createSpecification(TrainingTypeCriteria criteria) {
        Specification<TrainingType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TrainingType_.id));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), TrainingType_.nameEn));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), TrainingType_.nameAr));
            }
        }
        return specification;
    }
}
