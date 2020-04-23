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

import com.isoft.tms.domain.Attendees;
import com.isoft.tms.domain.*; // for static metamodels
import com.isoft.tms.repository.AttendeesRepository;
import com.isoft.tms.service.dto.AttendeesCriteria;
import com.isoft.tms.service.dto.AttendeesDTO;
import com.isoft.tms.service.mapper.AttendeesMapper;

/**
 * Service for executing complex queries for {@link Attendees} entities in the database.
 * The main input is a {@link AttendeesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttendeesDTO} or a {@link Page} of {@link AttendeesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendeesQueryService extends QueryService<Attendees> {

    private final Logger log = LoggerFactory.getLogger(AttendeesQueryService.class);

    private final AttendeesRepository attendeesRepository;

    private final AttendeesMapper attendeesMapper;

    public AttendeesQueryService(AttendeesRepository attendeesRepository, AttendeesMapper attendeesMapper) {
        this.attendeesRepository = attendeesRepository;
        this.attendeesMapper = attendeesMapper;
    }

    /**
     * Return a {@link List} of {@link AttendeesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttendeesDTO> findByCriteria(AttendeesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attendees> specification = createSpecification(criteria);
        return attendeesMapper.toDto(attendeesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttendeesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendeesDTO> findByCriteria(AttendeesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attendees> specification = createSpecification(criteria);
        return attendeesRepository.findAll(specification, page)
            .map(attendeesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttendeesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attendees> specification = createSpecification(criteria);
        return attendeesRepository.count(specification);
    }

    /**
     * Function to convert {@link AttendeesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Attendees> createSpecification(AttendeesCriteria criteria) {
        Specification<Attendees> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Attendees_.id));
            }
            if (criteria.getApplicantId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicantId(), Attendees_.applicantId));
            }
            if (criteria.getTrainingClassId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrainingClassId(),
                    root -> root.join(Attendees_.trainingClass, JoinType.LEFT).get(TrainingClass_.id)));
            }
        }
        return specification;
    }
}
