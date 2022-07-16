package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenceGenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceGenDTO.class);
        SequenceGenDTO sequenceGenDTO1 = new SequenceGenDTO();
        sequenceGenDTO1.setId(1L);
        SequenceGenDTO sequenceGenDTO2 = new SequenceGenDTO();
        assertThat(sequenceGenDTO1).isNotEqualTo(sequenceGenDTO2);
        sequenceGenDTO2.setId(sequenceGenDTO1.getId());
        assertThat(sequenceGenDTO1).isEqualTo(sequenceGenDTO2);
        sequenceGenDTO2.setId(2L);
        assertThat(sequenceGenDTO1).isNotEqualTo(sequenceGenDTO2);
        sequenceGenDTO1.setId(null);
        assertThat(sequenceGenDTO1).isNotEqualTo(sequenceGenDTO2);
    }
}
