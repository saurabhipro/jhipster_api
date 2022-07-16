package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandCompensationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandCompensationDTO.class);
        LandCompensationDTO landCompensationDTO1 = new LandCompensationDTO();
        landCompensationDTO1.setId(1L);
        LandCompensationDTO landCompensationDTO2 = new LandCompensationDTO();
        assertThat(landCompensationDTO1).isNotEqualTo(landCompensationDTO2);
        landCompensationDTO2.setId(landCompensationDTO1.getId());
        assertThat(landCompensationDTO1).isEqualTo(landCompensationDTO2);
        landCompensationDTO2.setId(2L);
        assertThat(landCompensationDTO1).isNotEqualTo(landCompensationDTO2);
        landCompensationDTO1.setId(null);
        assertThat(landCompensationDTO1).isNotEqualTo(landCompensationDTO2);
    }
}
