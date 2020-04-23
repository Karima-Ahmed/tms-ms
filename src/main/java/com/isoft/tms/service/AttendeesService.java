package com.isoft.tms.service;

import com.isoft.tms.service.dto.AttendeesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.Attendees}.
 */
public interface AttendeesService {

    /**
     * Save a attendees.
     *
     * @param attendeesDTO the entity to save.
     * @return the persisted entity.
     */
    AttendeesDTO save(AttendeesDTO attendeesDTO);

    /**
     * Get all the attendees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttendeesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attendees.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttendeesDTO> findOne(Long id);

    /**
     * Delete the "id" attendees.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
