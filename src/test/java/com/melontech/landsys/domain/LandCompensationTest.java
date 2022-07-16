package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandCompensationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandCompensation.class);
        LandCompensation landCompensation1 = new LandCompensation();
        landCompensation1.setId(1L);
        LandCompensation landCompensation2 = new LandCompensation();
        landCompensation2.setId(landCompensation1.getId());
        assertThat(landCompensation1).isEqualTo(landCompensation2);
        landCompensation2.setId(2L);
        assertThat(landCompensation1).isNotEqualTo(landCompensation2);
        landCompensation1.setId(null);
        assertThat(landCompensation1).isNotEqualTo(landCompensation2);
    }
}
