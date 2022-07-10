package com.melontech.landsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.melontech.landsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectStatusHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectStatusHistoryDTO.class);
        ProjectStatusHistoryDTO projectStatusHistoryDTO1 = new ProjectStatusHistoryDTO();
        projectStatusHistoryDTO1.setId(1L);
        ProjectStatusHistoryDTO projectStatusHistoryDTO2 = new ProjectStatusHistoryDTO();
        assertThat(projectStatusHistoryDTO1).isNotEqualTo(projectStatusHistoryDTO2);
        projectStatusHistoryDTO2.setId(projectStatusHistoryDTO1.getId());
        assertThat(projectStatusHistoryDTO1).isEqualTo(projectStatusHistoryDTO2);
        projectStatusHistoryDTO2.setId(2L);
        assertThat(projectStatusHistoryDTO1).isNotEqualTo(projectStatusHistoryDTO2);
        projectStatusHistoryDTO1.setId(null);
        assertThat(projectStatusHistoryDTO1).isNotEqualTo(projectStatusHistoryDTO2);
    }
}
