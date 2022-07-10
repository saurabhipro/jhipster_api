package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicNotificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicNotificationDTO.class);
        PublicNotificationDTO publicNotificationDTO1 = new PublicNotificationDTO();
        publicNotificationDTO1.setId(1L);
        PublicNotificationDTO publicNotificationDTO2 = new PublicNotificationDTO();
        assertThat(publicNotificationDTO1).isNotEqualTo(publicNotificationDTO2);
        publicNotificationDTO2.setId(publicNotificationDTO1.getId());
        assertThat(publicNotificationDTO1).isEqualTo(publicNotificationDTO2);
        publicNotificationDTO2.setId(2L);
        assertThat(publicNotificationDTO1).isNotEqualTo(publicNotificationDTO2);
        publicNotificationDTO1.setId(null);
        assertThat(publicNotificationDTO1).isNotEqualTo(publicNotificationDTO2);
    }
}
