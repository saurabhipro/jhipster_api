package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.KhatedayStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.Khatedar} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.KhatedarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /khatedars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class KhatedarCriteria implements Serializable, Criteria {

    /**
     * Class for filtering KhatedayStatus
     */
    public static class KhatedayStatusFilter extends Filter<KhatedayStatus> {

        public KhatedayStatusFilter() {}

        public KhatedayStatusFilter(KhatedayStatusFilter filter) {
            super(filter);
        }

        @Override
        public KhatedayStatusFilter copy() {
            return new KhatedayStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter caseFileNo;

    private StringFilter remarks;

    private KhatedayStatusFilter status;

    private LongFilter surveyId;

    private LongFilter landCompensationId;

    private LongFilter citizenId;

    private LongFilter projectLandId;

    private Boolean distinct;

    public KhatedarCriteria() {}

    public KhatedarCriteria(KhatedarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.caseFileNo = other.caseFileNo == null ? null : other.caseFileNo.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.surveyId = other.surveyId == null ? null : other.surveyId.copy();
        this.landCompensationId = other.landCompensationId == null ? null : other.landCompensationId.copy();
        this.citizenId = other.citizenId == null ? null : other.citizenId.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KhatedarCriteria copy() {
        return new KhatedarCriteria(this);
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

    public StringFilter getCaseFileNo() {
        return caseFileNo;
    }

    public StringFilter caseFileNo() {
        if (caseFileNo == null) {
            caseFileNo = new StringFilter();
        }
        return caseFileNo;
    }

    public void setCaseFileNo(StringFilter caseFileNo) {
        this.caseFileNo = caseFileNo;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public StringFilter remarks() {
        if (remarks == null) {
            remarks = new StringFilter();
        }
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public KhatedayStatusFilter getStatus() {
        return status;
    }

    public KhatedayStatusFilter status() {
        if (status == null) {
            status = new KhatedayStatusFilter();
        }
        return status;
    }

    public void setStatus(KhatedayStatusFilter status) {
        this.status = status;
    }

    public LongFilter getSurveyId() {
        return surveyId;
    }

    public LongFilter surveyId() {
        if (surveyId == null) {
            surveyId = new LongFilter();
        }
        return surveyId;
    }

    public void setSurveyId(LongFilter surveyId) {
        this.surveyId = surveyId;
    }

    public LongFilter getLandCompensationId() {
        return landCompensationId;
    }

    public LongFilter landCompensationId() {
        if (landCompensationId == null) {
            landCompensationId = new LongFilter();
        }
        return landCompensationId;
    }

    public void setLandCompensationId(LongFilter landCompensationId) {
        this.landCompensationId = landCompensationId;
    }

    public LongFilter getCitizenId() {
        return citizenId;
    }

    public LongFilter citizenId() {
        if (citizenId == null) {
            citizenId = new LongFilter();
        }
        return citizenId;
    }

    public void setCitizenId(LongFilter citizenId) {
        this.citizenId = citizenId;
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
        final KhatedarCriteria that = (KhatedarCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(caseFileNo, that.caseFileNo) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(status, that.status) &&
            Objects.equals(surveyId, that.surveyId) &&
            Objects.equals(landCompensationId, that.landCompensationId) &&
            Objects.equals(citizenId, that.citizenId) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caseFileNo, remarks, status, surveyId, landCompensationId, citizenId, projectLandId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhatedarCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (caseFileNo != null ? "caseFileNo=" + caseFileNo + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (surveyId != null ? "surveyId=" + surveyId + ", " : "") +
            (landCompensationId != null ? "landCompensationId=" + landCompensationId + ", " : "") +
            (citizenId != null ? "citizenId=" + citizenId + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
