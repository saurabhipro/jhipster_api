package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectLandDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectLandDTO.class);
        ProjectLandDTO projectLandDTO1 = new ProjectLandDTO();
        projectLandDTO1.setId(1L);
        ProjectLandDTO projectLandDTO2 = new ProjectLandDTO();
        assertThat(projectLandDTO1).isNotEqualTo(projectLandDTO2);
        projectLandDTO2.setId(projectLandDTO1.getId());
        assertThat(projectLandDTO1).isEqualTo(projectLandDTO2);
        projectLandDTO2.setId(2L);
        assertThat(projectLandDTO1).isNotEqualTo(projectLandDTO2);
        projectLandDTO1.setId(null);
        assertThat(projectLandDTO1).isNotEqualTo(projectLandDTO2);
    }
}
