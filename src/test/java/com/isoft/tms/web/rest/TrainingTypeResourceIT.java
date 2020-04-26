package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.TrainingType;
import com.isoft.tms.repository.TrainingTypeRepository;
import com.isoft.tms.service.TrainingTypeService;
import com.isoft.tms.service.dto.TrainingTypeDTO;
import com.isoft.tms.service.mapper.TrainingTypeMapper;
import com.isoft.tms.service.dto.TrainingTypeCriteria;
import com.isoft.tms.service.TrainingTypeQueryService;

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
 * Integration tests for the {@link TrainingTypeResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class TrainingTypeResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Autowired
    private TrainingTypeMapper trainingTypeMapper;

    @Autowired
    private TrainingTypeService trainingTypeService;

    @Autowired
    private TrainingTypeQueryService trainingTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingTypeMockMvc;

    private TrainingType trainingType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingType createEntity(EntityManager em) {
        TrainingType trainingType = new TrainingType()
            .nameEn(DEFAULT_NAME_EN)
            .nameAr(DEFAULT_NAME_AR);
        return trainingType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainingType createUpdatedEntity(EntityManager em) {
        TrainingType trainingType = new TrainingType()
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR);
        return trainingType;
    }

    @BeforeEach
    public void initTest() {
        trainingType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingType() throws Exception {
        int databaseSizeBeforeCreate = trainingTypeRepository.findAll().size();

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);
        restTrainingTypeMockMvc.perform(post("/api/training-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingType testTrainingType = trainingTypeList.get(trainingTypeList.size() - 1);
        assertThat(testTrainingType.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testTrainingType.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
    }

    @Test
    @Transactional
    public void createTrainingTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingTypeRepository.findAll().size();

        // Create the TrainingType with an existing ID
        trainingType.setId(1L);
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingTypeMockMvc.perform(post("/api/training-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingTypeRepository.findAll().size();
        // set the field null
        trainingType.setNameEn(null);

        // Create the TrainingType, which fails.
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        restTrainingTypeMockMvc.perform(post("/api/training-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainingTypes() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList
        restTrainingTypeMockMvc.perform(get("/api/training-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)));
    }
    
    @Test
    @Transactional
    public void getTrainingType() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get the trainingType
        restTrainingTypeMockMvc.perform(get("/api/training-types/{id}", trainingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainingType.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR));
    }


    @Test
    @Transactional
    public void getTrainingTypesByIdFiltering() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        Long id = trainingType.getId();

        defaultTrainingTypeShouldBeFound("id.equals=" + id);
        defaultTrainingTypeShouldNotBeFound("id.notEquals=" + id);

        defaultTrainingTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrainingTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultTrainingTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrainingTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTrainingTypesByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameEn equals to DEFAULT_NAME_EN
        defaultTrainingTypeShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the trainingTypeList where nameEn equals to UPDATED_NAME_EN
        defaultTrainingTypeShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameEn not equals to DEFAULT_NAME_EN
        defaultTrainingTypeShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the trainingTypeList where nameEn not equals to UPDATED_NAME_EN
        defaultTrainingTypeShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultTrainingTypeShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the trainingTypeList where nameEn equals to UPDATED_NAME_EN
        defaultTrainingTypeShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameEn is not null
        defaultTrainingTypeShouldBeFound("nameEn.specified=true");

        // Get all the trainingTypeList where nameEn is null
        defaultTrainingTypeShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrainingTypesByNameEnContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameEn contains DEFAULT_NAME_EN
        defaultTrainingTypeShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the trainingTypeList where nameEn contains UPDATED_NAME_EN
        defaultTrainingTypeShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameEn does not contain DEFAULT_NAME_EN
        defaultTrainingTypeShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the trainingTypeList where nameEn does not contain UPDATED_NAME_EN
        defaultTrainingTypeShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllTrainingTypesByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameAr equals to DEFAULT_NAME_AR
        defaultTrainingTypeShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the trainingTypeList where nameAr equals to UPDATED_NAME_AR
        defaultTrainingTypeShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameAr not equals to DEFAULT_NAME_AR
        defaultTrainingTypeShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the trainingTypeList where nameAr not equals to UPDATED_NAME_AR
        defaultTrainingTypeShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultTrainingTypeShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the trainingTypeList where nameAr equals to UPDATED_NAME_AR
        defaultTrainingTypeShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameAr is not null
        defaultTrainingTypeShouldBeFound("nameAr.specified=true");

        // Get all the trainingTypeList where nameAr is null
        defaultTrainingTypeShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrainingTypesByNameArContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameAr contains DEFAULT_NAME_AR
        defaultTrainingTypeShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the trainingTypeList where nameAr contains UPDATED_NAME_AR
        defaultTrainingTypeShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllTrainingTypesByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        // Get all the trainingTypeList where nameAr does not contain DEFAULT_NAME_AR
        defaultTrainingTypeShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the trainingTypeList where nameAr does not contain UPDATED_NAME_AR
        defaultTrainingTypeShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrainingTypeShouldBeFound(String filter) throws Exception {
        restTrainingTypeMockMvc.perform(get("/api/training-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)));

        // Check, that the count call also returns 1
        restTrainingTypeMockMvc.perform(get("/api/training-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrainingTypeShouldNotBeFound(String filter) throws Exception {
        restTrainingTypeMockMvc.perform(get("/api/training-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrainingTypeMockMvc.perform(get("/api/training-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTrainingType() throws Exception {
        // Get the trainingType
        restTrainingTypeMockMvc.perform(get("/api/training-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingType() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();

        // Update the trainingType
        TrainingType updatedTrainingType = trainingTypeRepository.findById(trainingType.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingType are not directly saved in db
        em.detach(updatedTrainingType);
        updatedTrainingType
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR);
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(updatedTrainingType);

        restTrainingTypeMockMvc.perform(put("/api/training-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
        TrainingType testTrainingType = trainingTypeList.get(trainingTypeList.size() - 1);
        assertThat(testTrainingType.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testTrainingType.getNameAr()).isEqualTo(UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingType() throws Exception {
        int databaseSizeBeforeUpdate = trainingTypeRepository.findAll().size();

        // Create the TrainingType
        TrainingTypeDTO trainingTypeDTO = trainingTypeMapper.toDto(trainingType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingTypeMockMvc.perform(put("/api/training-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingType in the database
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrainingType() throws Exception {
        // Initialize the database
        trainingTypeRepository.saveAndFlush(trainingType);

        int databaseSizeBeforeDelete = trainingTypeRepository.findAll().size();

        // Delete the trainingType
        restTrainingTypeMockMvc.perform(delete("/api/training-types/{id}", trainingType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainingType> trainingTypeList = trainingTypeRepository.findAll();
        assertThat(trainingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
