package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoticeStatusMapperTest {

    private NoticeStatusMapper noticeStatusMapper;

    @BeforeEach
    public void setUp() {
        noticeStatusMapper = new NoticeStatusMapperImpl();
    }
}
