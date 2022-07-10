package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KhatedarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Khatedar.class);
        Khatedar khatedar1 = new Khatedar();
        khatedar1.setId(1L);
        Khatedar khatedar2 = new Khatedar();
        khatedar2.setId(khatedar1.getId());
        assertThat(khatedar1).isEqualTo(khatedar2);
        khatedar2.setId(2L);
        assertThat(khatedar1).isNotEqualTo(khatedar2);
        khatedar1.setId(null);
        assertThat(khatedar1).isNotEqualTo(khatedar2);
    }
}
