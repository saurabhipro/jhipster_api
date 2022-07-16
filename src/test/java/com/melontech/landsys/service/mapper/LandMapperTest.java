package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LandMapperTest {

    private LandMapper landMapper;

    @BeforeEach
    public void setUp() {
        landMapper = new LandMapperImpl();
    }
}
