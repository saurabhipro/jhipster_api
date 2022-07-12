package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentAdviceDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAdviceDetails.class);
        PaymentAdviceDetails paymentAdviceDetails1 = new PaymentAdviceDetails();
        paymentAdviceDetails1.setId(1L);
        PaymentAdviceDetails paymentAdviceDetails2 = new PaymentAdviceDetails();
        paymentAdviceDetails2.setId(paymentAdviceDetails1.getId());
        assertThat(paymentAdviceDetails1).isEqualTo(paymentAdviceDetails2);
        paymentAdviceDetails2.setId(2L);
        assertThat(paymentAdviceDetails1).isNotEqualTo(paymentAdviceDetails2);
        paymentAdviceDetails1.setId(null);
        assertThat(paymentAdviceDetails1).isNotEqualTo(paymentAdviceDetails2);
    }
}
