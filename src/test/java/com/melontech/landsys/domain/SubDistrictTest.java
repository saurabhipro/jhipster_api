package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubDistrictTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubDistrict.class);
        SubDistrict subDistrict1 = new SubDistrict();
        subDistrict1.setId(1L);
        SubDistrict subDistrict2 = new SubDistrict();
        subDistrict2.setId(subDistrict1.getId());
        assertThat(subDistrict1).isEqualTo(subDistrict2);
        subDistrict2.setId(2L);
        assertThat(subDistrict1).isNotEqualTo(subDistrict2);
        subDistrict1.setId(null);
        assertThat(subDistrict1).isNotEqualTo(subDistrict2);
    }
}
