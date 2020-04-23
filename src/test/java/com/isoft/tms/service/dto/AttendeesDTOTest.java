package com.isoft.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class AttendeesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendeesDTO.class);
        AttendeesDTO attendeesDTO1 = new AttendeesDTO();
        attendeesDTO1.setId(1L);
        AttendeesDTO attendeesDTO2 = new AttendeesDTO();
        assertThat(attendeesDTO1).isNotEqualTo(attendeesDTO2);
        attendeesDTO2.setId(attendeesDTO1.getId());
        assertThat(attendeesDTO1).isEqualTo(attendeesDTO2);
        attendeesDTO2.setId(2L);
        assertThat(attendeesDTO1).isNotEqualTo(attendeesDTO2);
        attendeesDTO1.setId(null);
        assertThat(attendeesDTO1).isNotEqualTo(attendeesDTO2);
    }
}
