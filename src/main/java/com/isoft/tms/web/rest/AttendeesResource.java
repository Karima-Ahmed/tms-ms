package com.isoft.tms.web.rest;

import com.isoft.tms.service.AttendeesService;
import com.isoft.tms.web.rest.errors.BadRequestAlertException;
import com.isoft.tms.service.dto.AttendeesDTO;
import com.isoft.tms.service.dto.AttendeesCriteria;
import com.isoft.tms.service.AttendeesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.tms.domain.Attendees}.
 */
@RestController
@RequestMapping("/api")
public class AttendeesResource {

    private final Logger log = LoggerFactory.getLogger(AttendeesResource.class);

    private static final String ENTITY_NAME = "tmsMsAttendees";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendeesService attendeesService;

    private final AttendeesQueryService attendeesQueryService;

    public AttendeesResource(AttendeesService attendeesService, AttendeesQueryService attendeesQueryService) {
        this.attendeesService = attendeesService;
        this.attendeesQueryService = attendeesQueryService;
    }

    /**
     * {@code POST  /attendees} : Create a new attendees.
     *
     * @param attendeesDTO the attendeesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendeesDTO, or with status {@code 400 (Bad Request)} if the attendees has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendees")
    public ResponseEntity<AttendeesDTO> createAttendees(@Valid @RequestBody AttendeesDTO attendeesDTO) throws URISyntaxException {
        log.debug("REST request to save Attendees : {}", attendeesDTO);
        if (attendeesDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendeesDTO result = attendeesService.save(attendeesDTO);
        return ResponseEntity.created(new URI("/api/attendees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendees} : Updates an existing attendees.
     *
     * @param attendeesDTO the attendeesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendeesDTO,
     * or with status {@code 400 (Bad Request)} if the attendeesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendeesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendees")
    public ResponseEntity<AttendeesDTO> updateAttendees(@Valid @RequestBody AttendeesDTO attendeesDTO) throws URISyntaxException {
        log.debug("REST request to update Attendees : {}", attendeesDTO);
        if (attendeesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendeesDTO result = attendeesService.save(attendeesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendeesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendees} : get all the attendees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendees in body.
     */
    @GetMapping("/attendees")
    public ResponseEntity<List<AttendeesDTO>> getAllAttendees(AttendeesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Attendees by criteria: {}", criteria);
        Page<AttendeesDTO> page = attendeesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendees/count} : count all the attendees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/attendees/count")
    public ResponseEntity<Long> countAttendees(AttendeesCriteria criteria) {
        log.debug("REST request to count Attendees by criteria: {}", criteria);
        return ResponseEntity.ok().body(attendeesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /attendees/:id} : get the "id" attendees.
     *
     * @param id the id of the attendeesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendeesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesDTO> getAttendees(@PathVariable Long id) {
        log.debug("REST request to get Attendees : {}", id);
        Optional<AttendeesDTO> attendeesDTO = attendeesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendeesDTO);
    }

    /**
     * {@code DELETE  /attendees/:id} : delete the "id" attendees.
     *
     * @param id the id of the attendeesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendees/{id}")
    public ResponseEntity<Void> deleteAttendees(@PathVariable Long id) {
        log.debug("REST request to delete Attendees : {}", id);
        attendeesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
