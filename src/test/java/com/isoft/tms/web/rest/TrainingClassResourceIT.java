package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.TrainingClass;
import com.isoft.tms.domain.TrainingType;
import com.isoft.tms.repository.TrainingClassRepository;
import com.isoft.tms.service.TrainingClassService;
import com.isoft.tms.service.dto.TrainingClassDTO;
import com.isoft.tms.service.mapper.TrainingClassMapper;
import com.isoft.tms.service.dto.TrainingClassCriteria;
import com.isoft.tms.service.TrainingClassQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrainingClassResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class TrainingClassResourceIT {

    private static final Long DEFAULT_SLOT_ID = 1L;
    private static final Long UPDATED_SLOT_ID = 2L;
    private static final Long SMALLER_SLOT_ID = 1L - 1L;

    private static final String DEFAULT_DESC_EN = "AAAAAAAAAA";
    private static final String UPDATED_DESC_EN = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_AR = "AAAAAAAAAA";
    private static final String UPDATED_DESC_AR = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIME_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CENTER_ID = 1L;
    private static final Long UPDATED_CENTER_ID = 2L;
    private static final Long SMALLER_CENTER_ID = 1L - 1L;

    private static final Long DEFAULT_FACLITATOR_ID = 1L;
    private static final Long UPDATED_FACLITATOR_ID = 2L;
    private static final Long SMALLER_FACLITATOR_ID = 1L - 1L;

    @Autowired
    private TrainingClassRepository trainingClassRepository;

    @Autowired
    private TrainingClassMapper trainingClassMapper;

    @Autowired
    private TrainingClassService trainingClassService;

    @Autowired
    private TrainingClassQueryService trainingClassQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingClassMockMvc;

    private TrainingClass trainingClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingClass createEntity(EntityManager em) {
        TrainingClass trainingClass = new TrainingClass()
            .slotId(DEFAULT_SLOT_ID)
            .descEn(DEFAULT_DESC_EN)
            .descAr(DEFAULT_DESC_AR)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO)
            .centerId(DEFAULT_CENTER_ID)
            .faclitatorId(DEFAULT_FACLITATOR_ID);
        return trainingClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingClass createUpdatedEntity(EntityManager em) {
        TrainingClass trainingClass = new TrainingClass()
            .slotId(UPDATED_SLOT_ID)
            .descEn(UPDATED_DESC_EN)
            .descAr(UPDATED_DESC_AR)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .centerId(UPDATED_CENTER_ID)
            .faclitatorId(UPDATED_FACLITATOR_ID);
        return trainingClass;
    }

    @BeforeEach
    public void initTest() {
        trainingClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingClass() throws Exception {
        int databaseSizeBeforeCreate = trainingClassRepository.findAll().size();

        // Create the TrainingClass
        TrainingClassDTO trainingClassDTO = trainingClassMapper.toDto(trainingClass);
        restTrainingClassMockMvc.perform(post("/api/training-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingClassDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainingClass in the database
        List<TrainingClass> trainingClassList = trainingClassRepository.findAll();
        assertThat(trainingClassList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingClass testTrainingClass = trainingClassList.get(trainingClassList.size() - 1);
        assertThat(testTrainingClass.getSlotId()).isEqualTo(DEFAULT_SLOT_ID);
        assertThat(testTrainingClass.getDescEn()).isEqualTo(DEFAULT_DESC_EN);
        assertThat(testTrainingClass.getDescAr()).isEqualTo(DEFAULT_DESC_AR);
        assertThat(testTrainingClass.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testTrainingClass.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testTrainingClass.getCenterId()).isEqualTo(DEFAULT_CENTER_ID);
        assertThat(testTrainingClass.getFaclitatorId()).isEqualTo(DEFAULT_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void createTrainingClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingClassRepository.findAll().size();

        // Create the TrainingClass with an existing ID
        trainingClass.setId(1L);
        TrainingClassDTO trainingClassDTO = trainingClassMapper.toDto(trainingClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingClassMockMvc.perform(post("/api/training-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingClass in the database
        List<TrainingClass> trainingClassList = trainingClassRepository.findAll();
        assertThat(trainingClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTrainingClasses() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList
        restTrainingClassMockMvc.perform(get("/api/training-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].slotId").value(hasItem(DEFAULT_SLOT_ID.intValue())))
            .andExpect(jsonPath("$.[*].descEn").value(hasItem(DEFAULT_DESC_EN)))
            .andExpect(jsonPath("$.[*].descAr").value(hasItem(DEFAULT_DESC_AR)))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO.toString())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].faclitatorId").value(hasItem(DEFAULT_FACLITATOR_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getTrainingClass() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get the trainingClass
        restTrainingClassMockMvc.perform(get("/api/training-classes/{id}", trainingClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingClass.getId().intValue()))
            .andExpect(jsonPath("$.slotId").value(DEFAULT_SLOT_ID.intValue()))
            .andExpect(jsonPath("$.descEn").value(DEFAULT_DESC_EN))
            .andExpect(jsonPath("$.descAr").value(DEFAULT_DESC_AR))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM.toString()))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO.toString()))
            .andExpect(jsonPath("$.centerId").value(DEFAULT_CENTER_ID.intValue()))
            .andExpect(jsonPath("$.faclitatorId").value(DEFAULT_FACLITATOR_ID.intValue()));
    }


    @Test
    @Transactional
    public void getTrainingClassesByIdFiltering() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        Long id = trainingClass.getId();

        defaultTrainingClassShouldBeFound("id.equals=" + id);
        defaultTrainingClassShouldNotBeFound("id.notEquals=" + id);

        defaultTrainingClassShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainingClassShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainingClassShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainingClassShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId equals to DEFAULT_SLOT_ID
        defaultTrainingClassShouldBeFound("slotId.equals=" + DEFAULT_SLOT_ID);

        // Get all the trainingClassList where slotId equals to UPDATED_SLOT_ID
        defaultTrainingClassShouldNotBeFound("slotId.equals=" + UPDATED_SLOT_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId not equals to DEFAULT_SLOT_ID
        defaultTrainingClassShouldNotBeFound("slotId.notEquals=" + DEFAULT_SLOT_ID);

        // Get all the trainingClassList where slotId not equals to UPDATED_SLOT_ID
        defaultTrainingClassShouldBeFound("slotId.notEquals=" + UPDATED_SLOT_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId in DEFAULT_SLOT_ID or UPDATED_SLOT_ID
        defaultTrainingClassShouldBeFound("slotId.in=" + DEFAULT_SLOT_ID + "," + UPDATED_SLOT_ID);

        // Get all the trainingClassList where slotId equals to UPDATED_SLOT_ID
        defaultTrainingClassShouldNotBeFound("slotId.in=" + UPDATED_SLOT_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId is not null
        defaultTrainingClassShouldBeFound("slotId.specified=true");

        // Get all the trainingClassList where slotId is null
        defaultTrainingClassShouldNotBeFound("slotId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId is greater than or equal to DEFAULT_SLOT_ID
        defaultTrainingClassShouldBeFound("slotId.greaterThanOrEqual=" + DEFAULT_SLOT_ID);

        // Get all the trainingClassList where slotId is greater than or equal to UPDATED_SLOT_ID
        defaultTrainingClassShouldNotBeFound("slotId.greaterThanOrEqual=" + UPDATED_SLOT_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId is less than or equal to DEFAULT_SLOT_ID
        defaultTrainingClassShouldBeFound("slotId.lessThanOrEqual=" + DEFAULT_SLOT_ID);

        // Get all the trainingClassList where slotId is less than or equal to SMALLER_SLOT_ID
        defaultTrainingClassShouldNotBeFound("slotId.lessThanOrEqual=" + SMALLER_SLOT_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId is less than DEFAULT_SLOT_ID
        defaultTrainingClassShouldNotBeFound("slotId.lessThan=" + DEFAULT_SLOT_ID);

        // Get all the trainingClassList where slotId is less than UPDATED_SLOT_ID
        defaultTrainingClassShouldBeFound("slotId.lessThan=" + UPDATED_SLOT_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesBySlotIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where slotId is greater than DEFAULT_SLOT_ID
        defaultTrainingClassShouldNotBeFound("slotId.greaterThan=" + DEFAULT_SLOT_ID);

        // Get all the trainingClassList where slotId is greater than SMALLER_SLOT_ID
        defaultTrainingClassShouldBeFound("slotId.greaterThan=" + SMALLER_SLOT_ID);
    }


    @Test
    @Transactional
    public void getAllTrainingClassesByDescEnIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descEn equals to DEFAULT_DESC_EN
        defaultTrainingClassShouldBeFound("descEn.equals=" + DEFAULT_DESC_EN);

        // Get all the trainingClassList where descEn equals to UPDATED_DESC_EN
        defaultTrainingClassShouldNotBeFound("descEn.equals=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descEn not equals to DEFAULT_DESC_EN
        defaultTrainingClassShouldNotBeFound("descEn.notEquals=" + DEFAULT_DESC_EN);

        // Get all the trainingClassList where descEn not equals to UPDATED_DESC_EN
        defaultTrainingClassShouldBeFound("descEn.notEquals=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescEnIsInShouldWork() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descEn in DEFAULT_DESC_EN or UPDATED_DESC_EN
        defaultTrainingClassShouldBeFound("descEn.in=" + DEFAULT_DESC_EN + "," + UPDATED_DESC_EN);

        // Get all the trainingClassList where descEn equals to UPDATED_DESC_EN
        defaultTrainingClassShouldNotBeFound("descEn.in=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descEn is not null
        defaultTrainingClassShouldBeFound("descEn.specified=true");

        // Get all the trainingClassList where descEn is null
        defaultTrainingClassShouldNotBeFound("descEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrainingClassesByDescEnContainsSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descEn contains DEFAULT_DESC_EN
        defaultTrainingClassShouldBeFound("descEn.contains=" + DEFAULT_DESC_EN);

        // Get all the trainingClassList where descEn contains UPDATED_DESC_EN
        defaultTrainingClassShouldNotBeFound("descEn.contains=" + UPDATED_DESC_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescEnNotContainsSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descEn does not contain DEFAULT_DESC_EN
        defaultTrainingClassShouldNotBeFound("descEn.doesNotContain=" + DEFAULT_DESC_EN);

        // Get all the trainingClassList where descEn does not contain UPDATED_DESC_EN
        defaultTrainingClassShouldBeFound("descEn.doesNotContain=" + UPDATED_DESC_EN);
    }


    @Test
    @Transactional
    public void getAllTrainingClassesByDescArIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descAr equals to DEFAULT_DESC_AR
        defaultTrainingClassShouldBeFound("descAr.equals=" + DEFAULT_DESC_AR);

        // Get all the trainingClassList where descAr equals to UPDATED_DESC_AR
        defaultTrainingClassShouldNotBeFound("descAr.equals=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descAr not equals to DEFAULT_DESC_AR
        defaultTrainingClassShouldNotBeFound("descAr.notEquals=" + DEFAULT_DESC_AR);

        // Get all the trainingClassList where descAr not equals to UPDATED_DESC_AR
        defaultTrainingClassShouldBeFound("descAr.notEquals=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescArIsInShouldWork() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descAr in DEFAULT_DESC_AR or UPDATED_DESC_AR
        defaultTrainingClassShouldBeFound("descAr.in=" + DEFAULT_DESC_AR + "," + UPDATED_DESC_AR);

        // Get all the trainingClassList where descAr equals to UPDATED_DESC_AR
        defaultTrainingClassShouldNotBeFound("descAr.in=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescArIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descAr is not null
        defaultTrainingClassShouldBeFound("descAr.specified=true");

        // Get all the trainingClassList where descAr is null
        defaultTrainingClassShouldNotBeFound("descAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrainingClassesByDescArContainsSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descAr contains DEFAULT_DESC_AR
        defaultTrainingClassShouldBeFound("descAr.contains=" + DEFAULT_DESC_AR);

        // Get all the trainingClassList where descAr contains UPDATED_DESC_AR
        defaultTrainingClassShouldNotBeFound("descAr.contains=" + UPDATED_DESC_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByDescArNotContainsSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where descAr does not contain DEFAULT_DESC_AR
        defaultTrainingClassShouldNotBeFound("descAr.doesNotContain=" + DEFAULT_DESC_AR);

        // Get all the trainingClassList where descAr does not contain UPDATED_DESC_AR
        defaultTrainingClassShouldBeFound("descAr.doesNotContain=" + UPDATED_DESC_AR);
    }


    @Test
    @Transactional
    public void getAllTrainingClassesByTimeFromIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeFrom equals to DEFAULT_TIME_FROM
        defaultTrainingClassShouldBeFound("timeFrom.equals=" + DEFAULT_TIME_FROM);

        // Get all the trainingClassList where timeFrom equals to UPDATED_TIME_FROM
        defaultTrainingClassShouldNotBeFound("timeFrom.equals=" + UPDATED_TIME_FROM);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByTimeFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeFrom not equals to DEFAULT_TIME_FROM
        defaultTrainingClassShouldNotBeFound("timeFrom.notEquals=" + DEFAULT_TIME_FROM);

        // Get all the trainingClassList where timeFrom not equals to UPDATED_TIME_FROM
        defaultTrainingClassShouldBeFound("timeFrom.notEquals=" + UPDATED_TIME_FROM);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByTimeFromIsInShouldWork() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeFrom in DEFAULT_TIME_FROM or UPDATED_TIME_FROM
        defaultTrainingClassShouldBeFound("timeFrom.in=" + DEFAULT_TIME_FROM + "," + UPDATED_TIME_FROM);

        // Get all the trainingClassList where timeFrom equals to UPDATED_TIME_FROM
        defaultTrainingClassShouldNotBeFound("timeFrom.in=" + UPDATED_TIME_FROM);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByTimeFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeFrom is not null
        defaultTrainingClassShouldBeFound("timeFrom.specified=true");

        // Get all the trainingClassList where timeFrom is null
        defaultTrainingClassShouldNotBeFound("timeFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByTimeToIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeTo equals to DEFAULT_TIME_TO
        defaultTrainingClassShouldBeFound("timeTo.equals=" + DEFAULT_TIME_TO);

        // Get all the trainingClassList where timeTo equals to UPDATED_TIME_TO
        defaultTrainingClassShouldNotBeFound("timeTo.equals=" + UPDATED_TIME_TO);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByTimeToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeTo not equals to DEFAULT_TIME_TO
        defaultTrainingClassShouldNotBeFound("timeTo.notEquals=" + DEFAULT_TIME_TO);

        // Get all the trainingClassList where timeTo not equals to UPDATED_TIME_TO
        defaultTrainingClassShouldBeFound("timeTo.notEquals=" + UPDATED_TIME_TO);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByTimeToIsInShouldWork() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeTo in DEFAULT_TIME_TO or UPDATED_TIME_TO
        defaultTrainingClassShouldBeFound("timeTo.in=" + DEFAULT_TIME_TO + "," + UPDATED_TIME_TO);

        // Get all the trainingClassList where timeTo equals to UPDATED_TIME_TO
        defaultTrainingClassShouldNotBeFound("timeTo.in=" + UPDATED_TIME_TO);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByTimeToIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where timeTo is not null
        defaultTrainingClassShouldBeFound("timeTo.specified=true");

        // Get all the trainingClassList where timeTo is null
        defaultTrainingClassShouldNotBeFound("timeTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId equals to DEFAULT_CENTER_ID
        defaultTrainingClassShouldBeFound("centerId.equals=" + DEFAULT_CENTER_ID);

        // Get all the trainingClassList where centerId equals to UPDATED_CENTER_ID
        defaultTrainingClassShouldNotBeFound("centerId.equals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId not equals to DEFAULT_CENTER_ID
        defaultTrainingClassShouldNotBeFound("centerId.notEquals=" + DEFAULT_CENTER_ID);

        // Get all the trainingClassList where centerId not equals to UPDATED_CENTER_ID
        defaultTrainingClassShouldBeFound("centerId.notEquals=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId in DEFAULT_CENTER_ID or UPDATED_CENTER_ID
        defaultTrainingClassShouldBeFound("centerId.in=" + DEFAULT_CENTER_ID + "," + UPDATED_CENTER_ID);

        // Get all the trainingClassList where centerId equals to UPDATED_CENTER_ID
        defaultTrainingClassShouldNotBeFound("centerId.in=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId is not null
        defaultTrainingClassShouldBeFound("centerId.specified=true");

        // Get all the trainingClassList where centerId is null
        defaultTrainingClassShouldNotBeFound("centerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId is greater than or equal to DEFAULT_CENTER_ID
        defaultTrainingClassShouldBeFound("centerId.greaterThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the trainingClassList where centerId is greater than or equal to UPDATED_CENTER_ID
        defaultTrainingClassShouldNotBeFound("centerId.greaterThanOrEqual=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId is less than or equal to DEFAULT_CENTER_ID
        defaultTrainingClassShouldBeFound("centerId.lessThanOrEqual=" + DEFAULT_CENTER_ID);

        // Get all the trainingClassList where centerId is less than or equal to SMALLER_CENTER_ID
        defaultTrainingClassShouldNotBeFound("centerId.lessThanOrEqual=" + SMALLER_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId is less than DEFAULT_CENTER_ID
        defaultTrainingClassShouldNotBeFound("centerId.lessThan=" + DEFAULT_CENTER_ID);

        // Get all the trainingClassList where centerId is less than UPDATED_CENTER_ID
        defaultTrainingClassShouldBeFound("centerId.lessThan=" + UPDATED_CENTER_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByCenterIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where centerId is greater than DEFAULT_CENTER_ID
        defaultTrainingClassShouldNotBeFound("centerId.greaterThan=" + DEFAULT_CENTER_ID);

        // Get all the trainingClassList where centerId is greater than SMALLER_CENTER_ID
        defaultTrainingClassShouldBeFound("centerId.greaterThan=" + SMALLER_CENTER_ID);
    }


    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId equals to DEFAULT_FACLITATOR_ID
        defaultTrainingClassShouldBeFound("faclitatorId.equals=" + DEFAULT_FACLITATOR_ID);

        // Get all the trainingClassList where faclitatorId equals to UPDATED_FACLITATOR_ID
        defaultTrainingClassShouldNotBeFound("faclitatorId.equals=" + UPDATED_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId not equals to DEFAULT_FACLITATOR_ID
        defaultTrainingClassShouldNotBeFound("faclitatorId.notEquals=" + DEFAULT_FACLITATOR_ID);

        // Get all the trainingClassList where faclitatorId not equals to UPDATED_FACLITATOR_ID
        defaultTrainingClassShouldBeFound("faclitatorId.notEquals=" + UPDATED_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsInShouldWork() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId in DEFAULT_FACLITATOR_ID or UPDATED_FACLITATOR_ID
        defaultTrainingClassShouldBeFound("faclitatorId.in=" + DEFAULT_FACLITATOR_ID + "," + UPDATED_FACLITATOR_ID);

        // Get all the trainingClassList where faclitatorId equals to UPDATED_FACLITATOR_ID
        defaultTrainingClassShouldNotBeFound("faclitatorId.in=" + UPDATED_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId is not null
        defaultTrainingClassShouldBeFound("faclitatorId.specified=true");

        // Get all the trainingClassList where faclitatorId is null
        defaultTrainingClassShouldNotBeFound("faclitatorId.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId is greater than or equal to DEFAULT_FACLITATOR_ID
        defaultTrainingClassShouldBeFound("faclitatorId.greaterThanOrEqual=" + DEFAULT_FACLITATOR_ID);

        // Get all the trainingClassList where faclitatorId is greater than or equal to UPDATED_FACLITATOR_ID
        defaultTrainingClassShouldNotBeFound("faclitatorId.greaterThanOrEqual=" + UPDATED_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId is less than or equal to DEFAULT_FACLITATOR_ID
        defaultTrainingClassShouldBeFound("faclitatorId.lessThanOrEqual=" + DEFAULT_FACLITATOR_ID);

        // Get all the trainingClassList where faclitatorId is less than or equal to SMALLER_FACLITATOR_ID
        defaultTrainingClassShouldNotBeFound("faclitatorId.lessThanOrEqual=" + SMALLER_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId is less than DEFAULT_FACLITATOR_ID
        defaultTrainingClassShouldNotBeFound("faclitatorId.lessThan=" + DEFAULT_FACLITATOR_ID);

        // Get all the trainingClassList where faclitatorId is less than UPDATED_FACLITATOR_ID
        defaultTrainingClassShouldBeFound("faclitatorId.lessThan=" + UPDATED_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void getAllTrainingClassesByFaclitatorIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        // Get all the trainingClassList where faclitatorId is greater than DEFAULT_FACLITATOR_ID
        defaultTrainingClassShouldNotBeFound("faclitatorId.greaterThan=" + DEFAULT_FACLITATOR_ID);

        // Get all the trainingClassList where faclitatorId is greater than SMALLER_FACLITATOR_ID
        defaultTrainingClassShouldBeFound("faclitatorId.greaterThan=" + SMALLER_FACLITATOR_ID);
    }


    @Test
    @Transactional
    public void getAllTrainingClassesByTrainingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);
        TrainingType trainingType = TrainingTypeResourceIT.createEntity(em);
        em.persist(trainingType);
        em.flush();
        trainingClass.setTrainingType(trainingType);
        trainingClassRepository.saveAndFlush(trainingClass);
        Long trainingTypeId = trainingType.getId();

        // Get all the trainingClassList where trainingType equals to trainingTypeId
        defaultTrainingClassShouldBeFound("trainingTypeId.equals=" + trainingTypeId);

        // Get all the trainingClassList where trainingType equals to trainingTypeId + 1
        defaultTrainingClassShouldNotBeFound("trainingTypeId.equals=" + (trainingTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingClassShouldBeFound(String filter) throws Exception {
        restTrainingClassMockMvc.perform(get("/api/training-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].slotId").value(hasItem(DEFAULT_SLOT_ID.intValue())))
            .andExpect(jsonPath("$.[*].descEn").value(hasItem(DEFAULT_DESC_EN)))
            .andExpect(jsonPath("$.[*].descAr").value(hasItem(DEFAULT_DESC_AR)))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO.toString())))
            .andExpect(jsonPath("$.[*].centerId").value(hasItem(DEFAULT_CENTER_ID.intValue())))
            .andExpect(jsonPath("$.[*].faclitatorId").value(hasItem(DEFAULT_FACLITATOR_ID.intValue())));

        // Check, that the count call also returns 1
        restTrainingClassMockMvc.perform(get("/api/training-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingClassShouldNotBeFound(String filter) throws Exception {
        restTrainingClassMockMvc.perform(get("/api/training-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingClassMockMvc.perform(get("/api/training-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTrainingClass() throws Exception {
        // Get the trainingClass
        restTrainingClassMockMvc.perform(get("/api/training-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingClass() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        int databaseSizeBeforeUpdate = trainingClassRepository.findAll().size();

        // Update the trainingClass
        TrainingClass updatedTrainingClass = trainingClassRepository.findById(trainingClass.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingClass are not directly saved in db
        em.detach(updatedTrainingClass);
        updatedTrainingClass
            .slotId(UPDATED_SLOT_ID)
            .descEn(UPDATED_DESC_EN)
            .descAr(UPDATED_DESC_AR)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .centerId(UPDATED_CENTER_ID)
            .faclitatorId(UPDATED_FACLITATOR_ID);
        TrainingClassDTO trainingClassDTO = trainingClassMapper.toDto(updatedTrainingClass);

        restTrainingClassMockMvc.perform(put("/api/training-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingClassDTO)))
            .andExpect(status().isOk());

        // Validate the TrainingClass in the database
        List<TrainingClass> trainingClassList = trainingClassRepository.findAll();
        assertThat(trainingClassList).hasSize(databaseSizeBeforeUpdate);
        TrainingClass testTrainingClass = trainingClassList.get(trainingClassList.size() - 1);
        assertThat(testTrainingClass.getSlotId()).isEqualTo(UPDATED_SLOT_ID);
        assertThat(testTrainingClass.getDescEn()).isEqualTo(UPDATED_DESC_EN);
        assertThat(testTrainingClass.getDescAr()).isEqualTo(UPDATED_DESC_AR);
        assertThat(testTrainingClass.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testTrainingClass.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testTrainingClass.getCenterId()).isEqualTo(UPDATED_CENTER_ID);
        assertThat(testTrainingClass.getFaclitatorId()).isEqualTo(UPDATED_FACLITATOR_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingClass() throws Exception {
        int databaseSizeBeforeUpdate = trainingClassRepository.findAll().size();

        // Create the TrainingClass
        TrainingClassDTO trainingClassDTO = trainingClassMapper.toDto(trainingClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingClassMockMvc.perform(put("/api/training-classes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingClass in the database
        List<TrainingClass> trainingClassList = trainingClassRepository.findAll();
        assertThat(trainingClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrainingClass() throws Exception {
        // Initialize the database
        trainingClassRepository.saveAndFlush(trainingClass);

        int databaseSizeBeforeDelete = trainingClassRepository.findAll().size();

        // Delete the trainingClass
        restTrainingClassMockMvc.perform(delete("/api/training-classes/{id}", trainingClass.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingClass> trainingClassList = trainingClassRepository.findAll();
        assertThat(trainingClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
