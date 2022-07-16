package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitizenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Citizen.class);
        Citizen citizen1 = new Citizen();
        citizen1.setId(1L);
        Citizen citizen2 = new Citizen();
        citizen2.setId(citizen1.getId());
        assertThat(citizen1).isEqualTo(citizen2);
        citizen2.setId(2L);
        assertThat(citizen1).isNotEqualTo(citizen2);
        citizen1.setId(null);
        assertThat(citizen1).isNotEqualTo(citizen2);
    }
}
