package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrainingClassMapperTest {

    private TrainingClassMapper trainingClassMapper;

    @BeforeEach
    public void setUp() {
        trainingClassMapper = new TrainingClassMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(trainingClassMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(trainingClassMapper.fromId(null)).isNull();
    }
}
