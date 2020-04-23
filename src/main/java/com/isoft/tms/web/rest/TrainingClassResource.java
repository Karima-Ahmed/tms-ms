package com.isoft.tms.web.rest;

import com.isoft.tms.service.TrainingClassService;
import com.isoft.tms.web.rest.errors.BadRequestAlertException;
import com.isoft.tms.service.dto.TrainingClassDTO;
import com.isoft.tms.service.dto.TrainingClassCriteria;
import com.isoft.tms.service.TrainingClassQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.tms.domain.TrainingClass}.
 */
@RestController
@RequestMapping("/api")
public class TrainingClassResource {

    private final Logger log = LoggerFactory.getLogger(TrainingClassResource.class);

    private static final String ENTITY_NAME = "tmsMsTrainingClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingClassService trainingClassService;

    private final TrainingClassQueryService trainingClassQueryService;

    public TrainingClassResource(TrainingClassService trainingClassService, TrainingClassQueryService trainingClassQueryService) {
        this.trainingClassService = trainingClassService;
        this.trainingClassQueryService = trainingClassQueryService;
    }

    /**
     * {@code POST  /training-classes} : Create a new trainingClass.
     *
     * @param trainingClassDTO the trainingClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingClassDTO, or with status {@code 400 (Bad Request)} if the trainingClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/training-classes")
    public ResponseEntity<TrainingClassDTO> createTrainingClass(@RequestBody TrainingClassDTO trainingClassDTO) throws URISyntaxException {
        log.debug("REST request to save TrainingClass : {}", trainingClassDTO);
        if (trainingClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainingClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingClassDTO result = trainingClassService.save(trainingClassDTO);
        return ResponseEntity.created(new URI("/api/training-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /training-classes} : Updates an existing trainingClass.
     *
     * @param trainingClassDTO the trainingClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingClassDTO,
     * or with status {@code 400 (Bad Request)} if the trainingClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/training-classes")
    public ResponseEntity<TrainingClassDTO> updateTrainingClass(@RequestBody TrainingClassDTO trainingClassDTO) throws URISyntaxException {
        log.debug("REST request to update TrainingClass : {}", trainingClassDTO);
        if (trainingClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingClassDTO result = trainingClassService.save(trainingClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /training-classes} : get all the trainingClasses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainingClasses in body.
     */
    @GetMapping("/training-classes")
    public ResponseEntity<List<TrainingClassDTO>> getAllTrainingClasses(TrainingClassCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TrainingClasses by criteria: {}", criteria);
        Page<TrainingClassDTO> page = trainingClassQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /training-classes/count} : count all the trainingClasses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/training-classes/count")
    public ResponseEntity<Long> countTrainingClasses(TrainingClassCriteria criteria) {
        log.debug("REST request to count TrainingClasses by criteria: {}", criteria);
        return ResponseEntity.ok().body(trainingClassQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /training-classes/:id} : get the "id" trainingClass.
     *
     * @param id the id of the trainingClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/training-classes/{id}")
    public ResponseEntity<TrainingClassDTO> getTrainingClass(@PathVariable Long id) {
        log.debug("REST request to get TrainingClass : {}", id);
        Optional<TrainingClassDTO> trainingClassDTO = trainingClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingClassDTO);
    }

    /**
     * {@code DELETE  /training-classes/:id} : delete the "id" trainingClass.
     *
     * @param id the id of the trainingClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/training-classes/{id}")
    public ResponseEntity<Void> deleteTrainingClass(@PathVariable Long id) {
        log.debug("REST request to delete TrainingClass : {}", id);
        trainingClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
