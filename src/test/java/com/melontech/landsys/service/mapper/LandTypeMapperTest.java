package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LandTypeMapperTest {

    private LandTypeMapper landTypeMapper;

    @BeforeEach
    public void setUp() {
        landTypeMapper = new LandTypeMapperImpl();
    }
}
