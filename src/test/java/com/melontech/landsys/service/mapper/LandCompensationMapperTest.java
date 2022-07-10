package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LandCompensationMapperTest {

    private LandCompensationMapper landCompensationMapper;

    @BeforeEach
    public void setUp() {
        landCompensationMapper = new LandCompensationMapperImpl();
    }
}
