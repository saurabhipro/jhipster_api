package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectStatusHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectStatusHistory.class);
        ProjectStatusHistory projectStatusHistory1 = new ProjectStatusHistory();
        projectStatusHistory1.setId(1L);
        ProjectStatusHistory projectStatusHistory2 = new ProjectStatusHistory();
        projectStatusHistory2.setId(projectStatusHistory1.getId());
        assertThat(projectStatusHistory1).isEqualTo(projectStatusHistory2);
        projectStatusHistory2.setId(2L);
        assertThat(projectStatusHistory1).isNotEqualTo(projectStatusHistory2);
        projectStatusHistory1.setId(null);
        assertThat(projectStatusHistory1).isNotEqualTo(projectStatusHistory2);
    }
}
