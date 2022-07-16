package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenceGenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceGen.class);
        SequenceGen sequenceGen1 = new SequenceGen();
        sequenceGen1.setId(1L);
        SequenceGen sequenceGen2 = new SequenceGen();
        sequenceGen2.setId(sequenceGen1.getId());
        assertThat(sequenceGen1).isEqualTo(sequenceGen2);
        sequenceGen2.setId(2L);
        assertThat(sequenceGen1).isNotEqualTo(sequenceGen2);
        sequenceGen1.setId(null);
        assertThat(sequenceGen1).isNotEqualTo(sequenceGen2);
    }
}
