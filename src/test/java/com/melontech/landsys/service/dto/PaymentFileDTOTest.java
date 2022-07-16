package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFileDTO.class);
        PaymentFileDTO paymentFileDTO1 = new PaymentFileDTO();
        paymentFileDTO1.setId(1L);
        PaymentFileDTO paymentFileDTO2 = new PaymentFileDTO();
        assertThat(paymentFileDTO1).isNotEqualTo(paymentFileDTO2);
        paymentFileDTO2.setId(paymentFileDTO1.getId());
        assertThat(paymentFileDTO1).isEqualTo(paymentFileDTO2);
        paymentFileDTO2.setId(2L);
        assertThat(paymentFileDTO1).isNotEqualTo(paymentFileDTO2);
        paymentFileDTO1.setId(null);
        assertThat(paymentFileDTO1).isNotEqualTo(paymentFileDTO2);
    }
}
