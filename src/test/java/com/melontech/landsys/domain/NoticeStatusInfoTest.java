package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticeStatusInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticeStatusInfo.class);
        NoticeStatusInfo noticeStatusInfo1 = new NoticeStatusInfo();
        noticeStatusInfo1.setId(1L);
        NoticeStatusInfo noticeStatusInfo2 = new NoticeStatusInfo();
        noticeStatusInfo2.setId(noticeStatusInfo1.getId());
        assertThat(noticeStatusInfo1).isEqualTo(noticeStatusInfo2);
        noticeStatusInfo2.setId(2L);
        assertThat(noticeStatusInfo1).isNotEqualTo(noticeStatusInfo2);
        noticeStatusInfo1.setId(null);
        assertThat(noticeStatusInfo1).isNotEqualTo(noticeStatusInfo2);
    }
}
