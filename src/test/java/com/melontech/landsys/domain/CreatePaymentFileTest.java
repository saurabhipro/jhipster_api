package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreatePaymentFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreatePaymentFile.class);
        CreatePaymentFile createPaymentFile1 = new CreatePaymentFile();
        createPaymentFile1.setId(1L);
        CreatePaymentFile createPaymentFile2 = new CreatePaymentFile();
        createPaymentFile2.setId(createPaymentFile1.getId());
        assertThat(createPaymentFile1).isEqualTo(createPaymentFile2);
        createPaymentFile2.setId(2L);
        assertThat(createPaymentFile1).isNotEqualTo(createPaymentFile2);
        createPaymentFile1.setId(null);
        assertThat(createPaymentFile1).isNotEqualTo(createPaymentFile2);
    }
}
