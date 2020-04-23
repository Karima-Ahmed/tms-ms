package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.Attendees;
import com.isoft.tms.domain.TrainingClass;
import com.isoft.tms.repository.AttendeesRepository;
import com.isoft.tms.service.AttendeesService;
import com.isoft.tms.service.dto.AttendeesDTO;
import com.isoft.tms.service.mapper.AttendeesMapper;
import com.isoft.tms.service.dto.AttendeesCriteria;
import com.isoft.tms.service.AttendeesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AttendeesResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class AttendeesResourceIT {

    private static final Long DEFAULT_APPLICANT_ID = 1L;
    private static final Long UPDATED_APPLICANT_ID = 2L;
    private static final Long SMALLER_APPLICANT_ID = 1L - 1L;

    @Autowired
    private AttendeesRepository attendeesRepository;

    @Autowired
    private AttendeesMapper attendeesMapper;

    @Autowired
    private AttendeesService attendeesService;

    @Autowired
    private AttendeesQueryService attendeesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendeesMockMvc;

    private Attendees attendees;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendees createEntity(EntityManager em) {
        Attendees attendees = new Attendees()
            .applicantId(DEFAULT_APPLICANT_ID);
        return attendees;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendees createUpdatedEntity(EntityManager em) {
        Attendees attendees = new Attendees()
            .applicantId(UPDATED_APPLICANT_ID);
        return attendees;
    }

    @BeforeEach
    public void initTest() {
        attendees = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendees() throws Exception {
        int databaseSizeBeforeCreate = attendeesRepository.findAll().size();

        // Create the Attendees
        AttendeesDTO attendeesDTO = attendeesMapper.toDto(attendees);
        restAttendeesMockMvc.perform(post("/api/attendees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendeesDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendees in the database
        List<Attendees> attendeesList = attendeesRepository.findAll();
        assertThat(attendeesList).hasSize(databaseSizeBeforeCreate + 1);
        Attendees testAttendees = attendeesList.get(attendeesList.size() - 1);
        assertThat(testAttendees.getApplicantId()).isEqualTo(DEFAULT_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void createAttendeesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendeesRepository.findAll().size();

        // Create the Attendees with an existing ID
        attendees.setId(1L);
        AttendeesDTO attendeesDTO = attendeesMapper.toDto(attendees);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendeesMockMvc.perform(post("/api/attendees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendees in the database
        List<Attendees> attendeesList = attendeesRepository.findAll();
        assertThat(attendeesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendees() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList
        restAttendeesMockMvc.perform(get("/api/attendees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendees.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicantId").value(hasItem(DEFAULT_APPLICANT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getAttendees() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get the attendees
        restAttendeesMockMvc.perform(get("/api/attendees/{id}", attendees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendees.getId().intValue()))
            .andExpect(jsonPath("$.applicantId").value(DEFAULT_APPLICANT_ID.intValue()));
    }


    @Test
    @Transactional
    public void getAttendeesByIdFiltering() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        Long id = attendees.getId();

        defaultAttendeesShouldBeFound("id.equals=" + id);
        defaultAttendeesShouldNotBeFound("id.notEquals=" + id);

        defaultAttendeesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttendeesShouldNotBeFound("id.greaterThan=" + id);

        defaultAttendeesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttendeesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId equals to DEFAULT_APPLICANT_ID
        defaultAttendeesShouldBeFound("applicantId.equals=" + DEFAULT_APPLICANT_ID);

        // Get all the attendeesList where applicantId equals to UPDATED_APPLICANT_ID
        defaultAttendeesShouldNotBeFound("applicantId.equals=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId not equals to DEFAULT_APPLICANT_ID
        defaultAttendeesShouldNotBeFound("applicantId.notEquals=" + DEFAULT_APPLICANT_ID);

        // Get all the attendeesList where applicantId not equals to UPDATED_APPLICANT_ID
        defaultAttendeesShouldBeFound("applicantId.notEquals=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsInShouldWork() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId in DEFAULT_APPLICANT_ID or UPDATED_APPLICANT_ID
        defaultAttendeesShouldBeFound("applicantId.in=" + DEFAULT_APPLICANT_ID + "," + UPDATED_APPLICANT_ID);

        // Get all the attendeesList where applicantId equals to UPDATED_APPLICANT_ID
        defaultAttendeesShouldNotBeFound("applicantId.in=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId is not null
        defaultAttendeesShouldBeFound("applicantId.specified=true");

        // Get all the attendeesList where applicantId is null
        defaultAttendeesShouldNotBeFound("applicantId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId is greater than or equal to DEFAULT_APPLICANT_ID
        defaultAttendeesShouldBeFound("applicantId.greaterThanOrEqual=" + DEFAULT_APPLICANT_ID);

        // Get all the attendeesList where applicantId is greater than or equal to UPDATED_APPLICANT_ID
        defaultAttendeesShouldNotBeFound("applicantId.greaterThanOrEqual=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId is less than or equal to DEFAULT_APPLICANT_ID
        defaultAttendeesShouldBeFound("applicantId.lessThanOrEqual=" + DEFAULT_APPLICANT_ID);

        // Get all the attendeesList where applicantId is less than or equal to SMALLER_APPLICANT_ID
        defaultAttendeesShouldNotBeFound("applicantId.lessThanOrEqual=" + SMALLER_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsLessThanSomething() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId is less than DEFAULT_APPLICANT_ID
        defaultAttendeesShouldNotBeFound("applicantId.lessThan=" + DEFAULT_APPLICANT_ID);

        // Get all the attendeesList where applicantId is less than UPDATED_APPLICANT_ID
        defaultAttendeesShouldBeFound("applicantId.lessThan=" + UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void getAllAttendeesByApplicantIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        // Get all the attendeesList where applicantId is greater than DEFAULT_APPLICANT_ID
        defaultAttendeesShouldNotBeFound("applicantId.greaterThan=" + DEFAULT_APPLICANT_ID);

        // Get all the attendeesList where applicantId is greater than SMALLER_APPLICANT_ID
        defaultAttendeesShouldBeFound("applicantId.greaterThan=" + SMALLER_APPLICANT_ID);
    }


    @Test
    @Transactional
    public void getAllAttendeesByTrainingClassIsEqualToSomething() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);
        TrainingClass trainingClass = TrainingClassResourceIT.createEntity(em);
        em.persist(trainingClass);
        em.flush();
        attendees.setTrainingClass(trainingClass);
        attendeesRepository.saveAndFlush(attendees);
        Long trainingClassId = trainingClass.getId();

        // Get all the attendeesList where trainingClass equals to trainingClassId
        defaultAttendeesShouldBeFound("trainingClassId.equals=" + trainingClassId);

        // Get all the attendeesList where trainingClass equals to trainingClassId + 1
        defaultAttendeesShouldNotBeFound("trainingClassId.equals=" + (trainingClassId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttendeesShouldBeFound(String filter) throws Exception {
        restAttendeesMockMvc.perform(get("/api/attendees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendees.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicantId").value(hasItem(DEFAULT_APPLICANT_ID.intValue())));

        // Check, that the count call also returns 1
        restAttendeesMockMvc.perform(get("/api/attendees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttendeesShouldNotBeFound(String filter) throws Exception {
        restAttendeesMockMvc.perform(get("/api/attendees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendeesMockMvc.perform(get("/api/attendees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAttendees() throws Exception {
        // Get the attendees
        restAttendeesMockMvc.perform(get("/api/attendees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendees() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        int databaseSizeBeforeUpdate = attendeesRepository.findAll().size();

        // Update the attendees
        Attendees updatedAttendees = attendeesRepository.findById(attendees.getId()).get();
        // Disconnect from session so that the updates on updatedAttendees are not directly saved in db
        em.detach(updatedAttendees);
        updatedAttendees
            .applicantId(UPDATED_APPLICANT_ID);
        AttendeesDTO attendeesDTO = attendeesMapper.toDto(updatedAttendees);

        restAttendeesMockMvc.perform(put("/api/attendees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendeesDTO)))
            .andExpect(status().isOk());

        // Validate the Attendees in the database
        List<Attendees> attendeesList = attendeesRepository.findAll();
        assertThat(attendeesList).hasSize(databaseSizeBeforeUpdate);
        Attendees testAttendees = attendeesList.get(attendeesList.size() - 1);
        assertThat(testAttendees.getApplicantId()).isEqualTo(UPDATED_APPLICANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendees() throws Exception {
        int databaseSizeBeforeUpdate = attendeesRepository.findAll().size();

        // Create the Attendees
        AttendeesDTO attendeesDTO = attendeesMapper.toDto(attendees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendeesMockMvc.perform(put("/api/attendees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendees in the database
        List<Attendees> attendeesList = attendeesRepository.findAll();
        assertThat(attendeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendees() throws Exception {
        // Initialize the database
        attendeesRepository.saveAndFlush(attendees);

        int databaseSizeBeforeDelete = attendeesRepository.findAll().size();

        // Delete the attendees
        restAttendeesMockMvc.perform(delete("/api/attendees/{id}", attendees.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendees> attendeesList = attendeesRepository.findAll();
        assertThat(attendeesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
