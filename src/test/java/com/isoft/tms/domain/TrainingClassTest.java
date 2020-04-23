package com.isoft.tms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.isoft.tms.web.rest.TestUtil;

public class TrainingClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingClass.class);
        TrainingClass trainingClass1 = new TrainingClass();
        trainingClass1.setId(1L);
        TrainingClass trainingClass2 = new TrainingClass();
        trainingClass2.setId(trainingClass1.getId());
        assertThat(trainingClass1).isEqualTo(trainingClass2);
        trainingClass2.setId(2L);
        assertThat(trainingClass1).isNotEqualTo(trainingClass2);
        trainingClass1.setId(null);
        assertThat(trainingClass1).isNotEqualTo(trainingClass2);
    }
}
