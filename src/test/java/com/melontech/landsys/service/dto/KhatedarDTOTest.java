package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KhatedarDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KhatedarDTO.class);
        KhatedarDTO khatedarDTO1 = new KhatedarDTO();
        khatedarDTO1.setId(1L);
        KhatedarDTO khatedarDTO2 = new KhatedarDTO();
        assertThat(khatedarDTO1).isNotEqualTo(khatedarDTO2);
        khatedarDTO2.setId(khatedarDTO1.getId());
        assertThat(khatedarDTO1).isEqualTo(khatedarDTO2);
        khatedarDTO2.setId(2L);
        assertThat(khatedarDTO1).isNotEqualTo(khatedarDTO2);
        khatedarDTO1.setId(null);
        assertThat(khatedarDTO1).isNotEqualTo(khatedarDTO2);
    }
}
