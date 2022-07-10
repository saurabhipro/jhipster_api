package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticeStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticeStatus.class);
        NoticeStatus noticeStatus1 = new NoticeStatus();
        noticeStatus1.setId(1L);
        NoticeStatus noticeStatus2 = new NoticeStatus();
        noticeStatus2.setId(noticeStatus1.getId());
        assertThat(noticeStatus1).isEqualTo(noticeStatus2);
        noticeStatus2.setId(2L);
        assertThat(noticeStatus1).isNotEqualTo(noticeStatus2);
        noticeStatus1.setId(null);
        assertThat(noticeStatus1).isNotEqualTo(noticeStatus2);
    }
}
