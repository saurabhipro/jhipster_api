package com.melontech.landsys.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.Project} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.ProjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private DoubleFilter budget;

    private StringFilter approver1;

    private StringFilter approver2;

    private StringFilter approver3;

    private LongFilter landId;

    private LongFilter projectLandId;

    private Boolean distinct;

    public ProjectCriteria() {}

    public ProjectCriteria(ProjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.budget = other.budget == null ? null : other.budget.copy();
        this.approver1 = other.approver1 == null ? null : other.approver1.copy();
        this.approver2 = other.approver2 == null ? null : other.approver2.copy();
        this.approver3 = other.approver3 == null ? null : other.approver3.copy();
        this.landId = other.landId == null ? null : other.landId.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectCriteria copy() {
        return new ProjectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            endDate = new LocalDateFilter();
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public DoubleFilter getBudget() {
        return budget;
    }

    public DoubleFilter budget() {
        if (budget == null) {
            budget = new DoubleFilter();
        }
        return budget;
    }

    public void setBudget(DoubleFilter budget) {
        this.budget = budget;
    }

    public StringFilter getApprover1() {
        return approver1;
    }

    public StringFilter approver1() {
        if (approver1 == null) {
            approver1 = new StringFilter();
        }
        return approver1;
    }

    public void setApprover1(StringFilter approver1) {
        this.approver1 = approver1;
    }

    public StringFilter getApprover2() {
        return approver2;
    }

    public StringFilter approver2() {
        if (approver2 == null) {
            approver2 = new StringFilter();
        }
        return approver2;
    }

    public void setApprover2(StringFilter approver2) {
        this.approver2 = approver2;
    }

    public StringFilter getApprover3() {
        return approver3;
    }

    public StringFilter approver3() {
        if (approver3 == null) {
            approver3 = new StringFilter();
        }
        return approver3;
    }

    public void setApprover3(StringFilter approver3) {
        this.approver3 = approver3;
    }

    public LongFilter getLandId() {
        return landId;
    }

    public LongFilter landId() {
        if (landId == null) {
            landId = new LongFilter();
        }
        return landId;
    }

    public void setLandId(LongFilter landId) {
        this.landId = landId;
    }

    public LongFilter getProjectLandId() {
        return projectLandId;
    }

    public LongFilter projectLandId() {
        if (projectLandId == null) {
            projectLandId = new LongFilter();
        }
        return projectLandId;
    }

    public void setProjectLandId(LongFilter projectLandId) {
        this.projectLandId = projectLandId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCriteria that = (ProjectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(budget, that.budget) &&
            Objects.equals(approver1, that.approver1) &&
            Objects.equals(approver2, that.approver2) &&
            Objects.equals(approver3, that.approver3) &&
            Objects.equals(landId, that.landId) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, budget, approver1, approver2, approver3, landId, projectLandId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (budget != null ? "budget=" + budget + ", " : "") +
            (approver1 != null ? "approver1=" + approver1 + ", " : "") +
            (approver2 != null ? "approver2=" + approver2 + ", " : "") +
            (approver3 != null ? "approver3=" + approver3 + ", " : "") +
            (landId != null ? "landId=" + landId + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
