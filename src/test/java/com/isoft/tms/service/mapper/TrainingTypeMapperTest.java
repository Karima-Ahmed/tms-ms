package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrainingTypeMapperTest {

    private TrainingTypeMapper trainingTypeMapper;

    @BeforeEach
    public void setUp() {
        trainingTypeMapper = new TrainingTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(trainingTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(trainingTypeMapper.fromId(null)).isNull();
    }
}
