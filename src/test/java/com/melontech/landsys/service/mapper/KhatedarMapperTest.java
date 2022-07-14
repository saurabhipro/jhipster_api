package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KhatedarMapperTest {

    private KhatedarMapper khatedarMapper;

    @BeforeEach
    public void setUp() {
        khatedarMapper = new KhatedarMapperImpl();
    }
}
