package com.isoft.tms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttendeesMapperTest {

    private AttendeesMapper attendeesMapper;

    @BeforeEach
    public void setUp() {
        attendeesMapper = new AttendeesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attendeesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attendeesMapper.fromId(null)).isNull();
    }
}
