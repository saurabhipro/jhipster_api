package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CitizenMapperTest {

    private CitizenMapper citizenMapper;

    @BeforeEach
    public void setUp() {
        citizenMapper = new CitizenMapperImpl();
    }
}
