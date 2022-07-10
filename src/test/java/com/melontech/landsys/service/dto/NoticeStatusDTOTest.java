package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticeStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticeStatusDTO.class);
        NoticeStatusDTO noticeStatusDTO1 = new NoticeStatusDTO();
        noticeStatusDTO1.setId(1L);
        NoticeStatusDTO noticeStatusDTO2 = new NoticeStatusDTO();
        assertThat(noticeStatusDTO1).isNotEqualTo(noticeStatusDTO2);
        noticeStatusDTO2.setId(noticeStatusDTO1.getId());
        assertThat(noticeStatusDTO1).isEqualTo(noticeStatusDTO2);
        noticeStatusDTO2.setId(2L);
        assertThat(noticeStatusDTO1).isNotEqualTo(noticeStatusDTO2);
        noticeStatusDTO1.setId(null);
        assertThat(noticeStatusDTO1).isNotEqualTo(noticeStatusDTO2);
    }
}
