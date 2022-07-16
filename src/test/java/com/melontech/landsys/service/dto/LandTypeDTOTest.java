package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandTypeDTO.class);
        LandTypeDTO landTypeDTO1 = new LandTypeDTO();
        landTypeDTO1.setId(1L);
        LandTypeDTO landTypeDTO2 = new LandTypeDTO();
        assertThat(landTypeDTO1).isNotEqualTo(landTypeDTO2);
        landTypeDTO2.setId(landTypeDTO1.getId());
        assertThat(landTypeDTO1).isEqualTo(landTypeDTO2);
        landTypeDTO2.setId(2L);
        assertThat(landTypeDTO1).isNotEqualTo(landTypeDTO2);
        landTypeDTO1.setId(null);
        assertThat(landTypeDTO1).isNotEqualTo(landTypeDTO2);
    }
}
