package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentAdviceDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAdviceDetailsDTO.class);
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO1 = new PaymentAdviceDetailsDTO();
        paymentAdviceDetailsDTO1.setId(1L);
        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO2 = new PaymentAdviceDetailsDTO();
        assertThat(paymentAdviceDetailsDTO1).isNotEqualTo(paymentAdviceDetailsDTO2);
        paymentAdviceDetailsDTO2.setId(paymentAdviceDetailsDTO1.getId());
        assertThat(paymentAdviceDetailsDTO1).isEqualTo(paymentAdviceDetailsDTO2);
        paymentAdviceDetailsDTO2.setId(2L);
        assertThat(paymentAdviceDetailsDTO1).isNotEqualTo(paymentAdviceDetailsDTO2);
        paymentAdviceDetailsDTO1.setId(null);
        assertThat(paymentAdviceDetailsDTO1).isNotEqualTo(paymentAdviceDetailsDTO2);
    }
}
