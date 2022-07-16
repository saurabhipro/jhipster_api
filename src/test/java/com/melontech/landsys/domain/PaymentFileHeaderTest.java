package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFileHeaderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFileHeader.class);
        PaymentFileHeader paymentFileHeader1 = new PaymentFileHeader();
        paymentFileHeader1.setId(1L);
        PaymentFileHeader paymentFileHeader2 = new PaymentFileHeader();
        paymentFileHeader2.setId(paymentFileHeader1.getId());
        assertThat(paymentFileHeader1).isEqualTo(paymentFileHeader2);
        paymentFileHeader2.setId(2L);
        assertThat(paymentFileHeader1).isNotEqualTo(paymentFileHeader2);
        paymentFileHeader1.setId(null);
        assertThat(paymentFileHeader1).isNotEqualTo(paymentFileHeader2);
    }
}
