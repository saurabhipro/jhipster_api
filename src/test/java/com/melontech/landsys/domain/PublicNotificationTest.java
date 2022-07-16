package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PublicNotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicNotification.class);
        PublicNotification publicNotification1 = new PublicNotification();
        publicNotification1.setId(1L);
        PublicNotification publicNotification2 = new PublicNotification();
        publicNotification2.setId(publicNotification1.getId());
        assertThat(publicNotification1).isEqualTo(publicNotification2);
        publicNotification2.setId(2L);
        assertThat(publicNotification1).isNotEqualTo(publicNotification2);
        publicNotification1.setId(null);
        assertThat(publicNotification1).isNotEqualTo(publicNotification2);
    }
}
