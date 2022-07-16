package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreatePaymentFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreatePaymentFileDTO.class);
        CreatePaymentFileDTO createPaymentFileDTO1 = new CreatePaymentFileDTO();
        createPaymentFileDTO1.setId(1L);
        CreatePaymentFileDTO createPaymentFileDTO2 = new CreatePaymentFileDTO();
        assertThat(createPaymentFileDTO1).isNotEqualTo(createPaymentFileDTO2);
        createPaymentFileDTO2.setId(createPaymentFileDTO1.getId());
        assertThat(createPaymentFileDTO1).isEqualTo(createPaymentFileDTO2);
        createPaymentFileDTO2.setId(2L);
        assertThat(createPaymentFileDTO1).isNotEqualTo(createPaymentFileDTO2);
        createPaymentFileDTO1.setId(null);
        assertThat(createPaymentFileDTO1).isNotEqualTo(createPaymentFileDTO2);
    }
}
