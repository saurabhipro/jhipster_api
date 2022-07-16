package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandDTO.class);
        LandDTO landDTO1 = new LandDTO();
        landDTO1.setId(1L);
        LandDTO landDTO2 = new LandDTO();
        assertThat(landDTO1).isNotEqualTo(landDTO2);
        landDTO2.setId(landDTO1.getId());
        assertThat(landDTO1).isEqualTo(landDTO2);
        landDTO2.setId(2L);
        assertThat(landDTO1).isNotEqualTo(landDTO2);
        landDTO1.setId(null);
        assertThat(landDTO1).isNotEqualTo(landDTO2);
    }
}
