package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandType.class);
        LandType landType1 = new LandType();
        landType1.setId(1L);
        LandType landType2 = new LandType();
        landType2.setId(landType1.getId());
        assertThat(landType1).isEqualTo(landType2);
        landType2.setId(2L);
        assertThat(landType1).isNotEqualTo(landType2);
        landType1.setId(null);
        assertThat(landType1).isNotEqualTo(landType2);
    }
}
