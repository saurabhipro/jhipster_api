package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Project} entity.
 */
public class ProjectDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private Double budget;

    private String approver1;

    private String approver2;

    private String approver3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getApprover1() {
        return approver1;
    }

    public void setApprover1(String approver1) {
        this.approver1 = approver1;
    }

    public String getApprover2() {
        return approver2;
    }

    public void setApprover2(String approver2) {
        this.approver2 = approver2;
    }

    public String getApprover3() {
        return approver3;
    }

    public void setApprover3(String approver3) {
        this.approver3 = approver3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", budget=" + getBudget() +
            ", approver1='" + getApprover1() + "'" +
            ", approver2='" + getApprover2() + "'" +
            ", approver3='" + getApprover3() + "'" +
            "}";
    }
}
