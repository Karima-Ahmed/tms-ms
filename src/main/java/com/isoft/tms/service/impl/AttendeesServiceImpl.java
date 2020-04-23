package com.isoft.tms.service.impl;

import com.isoft.tms.service.AttendeesService;
import com.isoft.tms.domain.Attendees;
import com.isoft.tms.repository.AttendeesRepository;
import com.isoft.tms.service.dto.AttendeesDTO;
import com.isoft.tms.service.mapper.AttendeesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Attendees}.
 */
@Service
@Transactional
public class AttendeesServiceImpl implements AttendeesService {

    private final Logger log = LoggerFactory.getLogger(AttendeesServiceImpl.class);

    private final AttendeesRepository attendeesRepository;

    private final AttendeesMapper attendeesMapper;

    public AttendeesServiceImpl(AttendeesRepository attendeesRepository, AttendeesMapper attendeesMapper) {
        this.attendeesRepository = attendeesRepository;
        this.attendeesMapper = attendeesMapper;
    }

    /**
     * Save a attendees.
     *
     * @param attendeesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AttendeesDTO save(AttendeesDTO attendeesDTO) {
        log.debug("Request to save Attendees : {}", attendeesDTO);
        Attendees attendees = attendeesMapper.toEntity(attendeesDTO);
        attendees = attendeesRepository.save(attendees);
        return attendeesMapper.toDto(attendees);
    }

    /**
     * Get all the attendees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttendeesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attendees");
        return attendeesRepository.findAll(pageable)
            .map(attendeesMapper::toDto);
    }

    /**
     * Get one attendees by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttendeesDTO> findOne(Long id) {
        log.debug("Request to get Attendees : {}", id);
        return attendeesRepository.findById(id)
            .map(attendeesMapper::toDto);
    }

    /**
     * Delete the attendees by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attendees : {}", id);
        attendeesRepository.deleteById(id);
    }
}
