package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentAdviceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAdviceDTO.class);
        PaymentAdviceDTO paymentAdviceDTO1 = new PaymentAdviceDTO();
        paymentAdviceDTO1.setId(1L);
        PaymentAdviceDTO paymentAdviceDTO2 = new PaymentAdviceDTO();
        assertThat(paymentAdviceDTO1).isNotEqualTo(paymentAdviceDTO2);
        paymentAdviceDTO2.setId(paymentAdviceDTO1.getId());
        assertThat(paymentAdviceDTO1).isEqualTo(paymentAdviceDTO2);
        paymentAdviceDTO2.setId(2L);
        assertThat(paymentAdviceDTO1).isNotEqualTo(paymentAdviceDTO2);
        paymentAdviceDTO1.setId(null);
        assertThat(paymentAdviceDTO1).isNotEqualTo(paymentAdviceDTO2);
    }
}
