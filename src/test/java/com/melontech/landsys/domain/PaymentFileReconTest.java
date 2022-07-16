package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFileReconTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFileRecon.class);
        PaymentFileRecon paymentFileRecon1 = new PaymentFileRecon();
        paymentFileRecon1.setId(1L);
        PaymentFileRecon paymentFileRecon2 = new PaymentFileRecon();
        paymentFileRecon2.setId(paymentFileRecon1.getId());
        assertThat(paymentFileRecon1).isEqualTo(paymentFileRecon2);
        paymentFileRecon2.setId(2L);
        assertThat(paymentFileRecon1).isNotEqualTo(paymentFileRecon2);
        paymentFileRecon1.setId(null);
        assertThat(paymentFileRecon1).isNotEqualTo(paymentFileRecon2);
    }
}
