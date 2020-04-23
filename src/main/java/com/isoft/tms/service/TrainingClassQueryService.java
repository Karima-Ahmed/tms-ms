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

import com.isoft.tms.domain.TrainingClass;
import com.isoft.tms.domain.*; // for static metamodels
import com.isoft.tms.repository.TrainingClassRepository;
import com.isoft.tms.service.dto.TrainingClassCriteria;
import com.isoft.tms.service.dto.TrainingClassDTO;
import com.isoft.tms.service.mapper.TrainingClassMapper;

/**
 * Service for executing complex queries for {@link TrainingClass} entities in the database.
 * The main input is a {@link TrainingClassCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainingClassDTO} or a {@link Page} of {@link TrainingClassDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingClassQueryService extends QueryService<TrainingClass> {

    private final Logger log = LoggerFactory.getLogger(TrainingClassQueryService.class);

    private final TrainingClassRepository trainingClassRepository;

    private final TrainingClassMapper trainingClassMapper;

    public TrainingClassQueryService(TrainingClassRepository trainingClassRepository, TrainingClassMapper trainingClassMapper) {
        this.trainingClassRepository = trainingClassRepository;
        this.trainingClassMapper = trainingClassMapper;
    }

    /**
     * Return a {@link List} of {@link TrainingClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingClassDTO> findByCriteria(TrainingClassCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TrainingClass> specification = createSpecification(criteria);
        return trainingClassMapper.toDto(trainingClassRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainingClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingClassDTO> findByCriteria(TrainingClassCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrainingClass> specification = createSpecification(criteria);
        return trainingClassRepository.findAll(specification, page)
            .map(trainingClassMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingClassCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TrainingClass> specification = createSpecification(criteria);
        return trainingClassRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingClassCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TrainingClass> createSpecification(TrainingClassCriteria criteria) {
        Specification<TrainingClass> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TrainingClass_.id));
            }
            if (criteria.getSlotId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSlotId(), TrainingClass_.slotId));
            }
            if (criteria.getDescEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescEn(), TrainingClass_.descEn));
            }
            if (criteria.getDescAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescAr(), TrainingClass_.descAr));
            }
            if (criteria.getTimeFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeFrom(), TrainingClass_.timeFrom));
            }
            if (criteria.getTimeTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeTo(), TrainingClass_.timeTo));
            }
            if (criteria.getCenterId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCenterId(), TrainingClass_.centerId));
            }
            if (criteria.getFaclitatorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFaclitatorId(), TrainingClass_.faclitatorId));
            }
            if (criteria.getTrainingTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrainingTypeId(),
                    root -> root.join(TrainingClass_.trainingType, JoinType.LEFT).get(TrainingType_.id)));
            }
        }
        return specification;
    }
}
