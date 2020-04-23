package com.isoft.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class AttendeesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendees.class);
        Attendees attendees1 = new Attendees();
        attendees1.setId(1L);
        Attendees attendees2 = new Attendees();
        attendees2.setId(attendees1.getId());
        assertThat(attendees1).isEqualTo(attendees2);
        attendees2.setId(2L);
        assertThat(attendees1).isNotEqualTo(attendees2);
        attendees1.setId(null);
        assertThat(attendees1).isNotEqualTo(attendees2);
    }
}
