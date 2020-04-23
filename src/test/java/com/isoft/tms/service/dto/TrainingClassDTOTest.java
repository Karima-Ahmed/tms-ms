package com.isoft.tms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class TrainingClassDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingClassDTO.class);
        TrainingClassDTO trainingClassDTO1 = new TrainingClassDTO();
        trainingClassDTO1.setId(1L);
        TrainingClassDTO trainingClassDTO2 = new TrainingClassDTO();
        assertThat(trainingClassDTO1).isNotEqualTo(trainingClassDTO2);
        trainingClassDTO2.setId(trainingClassDTO1.getId());
        assertThat(trainingClassDTO1).isEqualTo(trainingClassDTO2);
        trainingClassDTO2.setId(2L);
        assertThat(trainingClassDTO1).isNotEqualTo(trainingClassDTO2);
        trainingClassDTO1.setId(null);
        assertThat(trainingClassDTO1).isNotEqualTo(trainingClassDTO2);
    }
}
