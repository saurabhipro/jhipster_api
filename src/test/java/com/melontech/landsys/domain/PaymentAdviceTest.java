package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentAdviceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAdvice.class);
        PaymentAdvice paymentAdvice1 = new PaymentAdvice();
        paymentAdvice1.setId(1L);
        PaymentAdvice paymentAdvice2 = new PaymentAdvice();
        paymentAdvice2.setId(paymentAdvice1.getId());
        assertThat(paymentAdvice1).isEqualTo(paymentAdvice2);
        paymentAdvice2.setId(2L);
        assertThat(paymentAdvice1).isNotEqualTo(paymentAdvice2);
        paymentAdvice1.setId(null);
        assertThat(paymentAdvice1).isNotEqualTo(paymentAdvice2);
    }
}
