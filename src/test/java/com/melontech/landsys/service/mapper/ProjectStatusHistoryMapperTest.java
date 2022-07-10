package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectStatusHistoryMapperTest {

    private ProjectStatusHistoryMapper projectStatusHistoryMapper;

    @BeforeEach
    public void setUp() {
        projectStatusHistoryMapper = new ProjectStatusHistoryMapperImpl();
    }
}
