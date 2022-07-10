package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubDistrictMapperTest {

    private SubDistrictMapper subDistrictMapper;

    @BeforeEach
    public void setUp() {
        subDistrictMapper = new SubDistrictMapperImpl();
    }
}
