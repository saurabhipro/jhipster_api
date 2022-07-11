package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFile.class);
        PaymentFile paymentFile1 = new PaymentFile();
        paymentFile1.setId(1L);
        PaymentFile paymentFile2 = new PaymentFile();
        paymentFile2.setId(paymentFile1.getId());
        assertThat(paymentFile1).isEqualTo(paymentFile2);
        paymentFile2.setId(2L);
        assertThat(paymentFile1).isNotEqualTo(paymentFile2);
        paymentFile1.setId(null);
        assertThat(paymentFile1).isNotEqualTo(paymentFile2);
    }
}
