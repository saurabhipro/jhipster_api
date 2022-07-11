package com.melontech.landsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoticeStatusInfoMapperTest {

    private NoticeStatusInfoMapper noticeStatusInfoMapper;

    @BeforeEach
    public void setUp() {
        noticeStatusInfoMapper = new NoticeStatusInfoMapperImpl();
    }
}
