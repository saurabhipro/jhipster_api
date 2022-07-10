package com.melontech.landsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectLandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectLand.class);
        ProjectLand projectLand1 = new ProjectLand();
        projectLand1.setId(1L);
        ProjectLand projectLand2 = new ProjectLand();
        projectLand2.setId(projectLand1.getId());
        assertThat(projectLand1).isEqualTo(projectLand2);
        projectLand2.setId(2L);
        assertThat(projectLand1).isNotEqualTo(projectLand2);
        projectLand1.setId(null);
        assertThat(projectLand1).isNotEqualTo(projectLand2);
    }
}
