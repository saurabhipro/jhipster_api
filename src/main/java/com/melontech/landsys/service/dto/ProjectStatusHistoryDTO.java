package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.ProjectStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.melontech.landsys.domain.ProjectStatusHistory} entity.
 */
public class ProjectStatusHistoryDTO implements Serializable {

    private Long id;

    private ProjectStatus status;

    private Instant when;

    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectStatusHistoryDTO)) {
            return false;
        }

        ProjectStatusHistoryDTO projectStatusHistoryDTO = (ProjectStatusHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectStatusHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectStatusHistoryDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", when='" + getWhen() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
