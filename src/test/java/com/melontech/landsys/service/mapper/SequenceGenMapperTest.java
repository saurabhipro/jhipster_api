package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SequenceGenMapperTest {

    private SequenceGenMapper sequenceGenMapper;

    @BeforeEach
    public void setUp() {
        sequenceGenMapper = new SequenceGenMapperImpl();
    }
}
