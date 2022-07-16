package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentFileHeaderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentFileHeaderDTO.class);
        PaymentFileHeaderDTO paymentFileHeaderDTO1 = new PaymentFileHeaderDTO();
        paymentFileHeaderDTO1.setId(1L);
        PaymentFileHeaderDTO paymentFileHeaderDTO2 = new PaymentFileHeaderDTO();
        assertThat(paymentFileHeaderDTO1).isNotEqualTo(paymentFileHeaderDTO2);
        paymentFileHeaderDTO2.setId(paymentFileHeaderDTO1.getId());
        assertThat(paymentFileHeaderDTO1).isEqualTo(paymentFileHeaderDTO2);
        paymentFileHeaderDTO2.setId(2L);
        assertThat(paymentFileHeaderDTO1).isNotEqualTo(paymentFileHeaderDTO2);
        paymentFileHeaderDTO1.setId(null);
        assertThat(paymentFileHeaderDTO1).isNotEqualTo(paymentFileHeaderDTO2);
    }
}
