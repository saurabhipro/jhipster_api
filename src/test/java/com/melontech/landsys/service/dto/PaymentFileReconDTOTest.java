package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFileReconDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFileReconDTO.class);
        PaymentFileReconDTO paymentFileReconDTO1 = new PaymentFileReconDTO();
        paymentFileReconDTO1.setId(1L);
        PaymentFileReconDTO paymentFileReconDTO2 = new PaymentFileReconDTO();
        assertThat(paymentFileReconDTO1).isNotEqualTo(paymentFileReconDTO2);
        paymentFileReconDTO2.setId(paymentFileReconDTO1.getId());
        assertThat(paymentFileReconDTO1).isEqualTo(paymentFileReconDTO2);
        paymentFileReconDTO2.setId(2L);
        assertThat(paymentFileReconDTO1).isNotEqualTo(paymentFileReconDTO2);
        paymentFileReconDTO1.setId(null);
        assertThat(paymentFileReconDTO1).isNotEqualTo(paymentFileReconDTO2);
    }
}
