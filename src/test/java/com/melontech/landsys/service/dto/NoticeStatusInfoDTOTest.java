package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticeStatusInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticeStatusInfoDTO.class);
        NoticeStatusInfoDTO noticeStatusInfoDTO1 = new NoticeStatusInfoDTO();
        noticeStatusInfoDTO1.setId(1L);
        NoticeStatusInfoDTO noticeStatusInfoDTO2 = new NoticeStatusInfoDTO();
        assertThat(noticeStatusInfoDTO1).isNotEqualTo(noticeStatusInfoDTO2);
        noticeStatusInfoDTO2.setId(noticeStatusInfoDTO1.getId());
        assertThat(noticeStatusInfoDTO1).isEqualTo(noticeStatusInfoDTO2);
        noticeStatusInfoDTO2.setId(2L);
        assertThat(noticeStatusInfoDTO1).isNotEqualTo(noticeStatusInfoDTO2);
        noticeStatusInfoDTO1.setId(null);
        assertThat(noticeStatusInfoDTO1).isNotEqualTo(noticeStatusInfoDTO2);
    }
}
