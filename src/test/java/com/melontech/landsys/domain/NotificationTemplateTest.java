package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationTemplate.class);
        NotificationTemplate notificationTemplate1 = new NotificationTemplate();
        notificationTemplate1.setId(1L);
        NotificationTemplate notificationTemplate2 = new NotificationTemplate();
        notificationTemplate2.setId(notificationTemplate1.getId());
        assertThat(notificationTemplate1).isEqualTo(notificationTemplate2);
        notificationTemplate2.setId(2L);
        assertThat(notificationTemplate1).isNotEqualTo(notificationTemplate2);
        notificationTemplate1.setId(null);
        assertThat(notificationTemplate1).isNotEqualTo(notificationTemplate2);
    }
}
