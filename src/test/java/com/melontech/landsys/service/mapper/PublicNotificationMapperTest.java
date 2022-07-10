package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicNotificationMapperTest {

    private PublicNotificationMapper publicNotificationMapper;

    @BeforeEach
    public void setUp() {
        publicNotificationMapper = new PublicNotificationMapperImpl();
    }
}
