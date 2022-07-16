package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectLandMapperTest {

    private ProjectLandMapper projectLandMapper;

    @BeforeEach
    public void setUp() {
        projectLandMapper = new ProjectLandMapperImpl();
    }
}
