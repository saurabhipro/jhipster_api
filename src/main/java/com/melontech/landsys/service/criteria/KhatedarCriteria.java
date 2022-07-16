package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.KhatedarStatus;
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
     * Class for filtering KhatedarStatus
     */
    public static class KhatedarStatusFilter extends Filter<KhatedarStatus> {

        public KhatedarStatusFilter() {}

        public KhatedarStatusFilter(KhatedarStatusFilter filter) {
            super(filter);
        }

        @Override
        public KhatedarStatusFilter copy() {
            return new KhatedarStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter caseFileNo;

    private StringFilter remarks;

    private KhatedarStatusFilter khatedarStatus;

    private LongFilter projectLandId;

    private LongFilter citizenId;

    private LongFilter paymentAdviceId;

    private LongFilter paymentFileId;

    private Boolean distinct;

    public KhatedarCriteria() {}

    public KhatedarCriteria(KhatedarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.caseFileNo = other.caseFileNo == null ? null : other.caseFileNo.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.khatedarStatus = other.khatedarStatus == null ? null : other.khatedarStatus.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.citizenId = other.citizenId == null ? null : other.citizenId.copy();
        this.paymentAdviceId = other.paymentAdviceId == null ? null : other.paymentAdviceId.copy();
        this.paymentFileId = other.paymentFileId == null ? null : other.paymentFileId.copy();
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

    public KhatedarStatusFilter getKhatedarStatus() {
        return khatedarStatus;
    }

    public KhatedarStatusFilter khatedarStatus() {
        if (khatedarStatus == null) {
            khatedarStatus = new KhatedarStatusFilter();
        }
        return khatedarStatus;
    }

    public void setKhatedarStatus(KhatedarStatusFilter khatedarStatus) {
        this.khatedarStatus = khatedarStatus;
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

    public LongFilter getPaymentAdviceId() {
        return paymentAdviceId;
    }

    public LongFilter paymentAdviceId() {
        if (paymentAdviceId == null) {
            paymentAdviceId = new LongFilter();
        }
        return paymentAdviceId;
    }

    public void setPaymentAdviceId(LongFilter paymentAdviceId) {
        this.paymentAdviceId = paymentAdviceId;
    }

    public LongFilter getPaymentFileId() {
        return paymentFileId;
    }

    public LongFilter paymentFileId() {
        if (paymentFileId == null) {
            paymentFileId = new LongFilter();
        }
        return paymentFileId;
    }

    public void setPaymentFileId(LongFilter paymentFileId) {
        this.paymentFileId = paymentFileId;
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
            Objects.equals(khatedarStatus, that.khatedarStatus) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(citizenId, that.citizenId) &&
            Objects.equals(paymentAdviceId, that.paymentAdviceId) &&
            Objects.equals(paymentFileId, that.paymentFileId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caseFileNo, remarks, khatedarStatus, projectLandId, citizenId, paymentAdviceId, paymentFileId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhatedarCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (caseFileNo != null ? "caseFileNo=" + caseFileNo + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (khatedarStatus != null ? "khatedarStatus=" + khatedarStatus + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (citizenId != null ? "citizenId=" + citizenId + ", " : "") +
            (paymentAdviceId != null ? "paymentAdviceId=" + paymentAdviceId + ", " : "") +
            (paymentFileId != null ? "paymentFileId=" + paymentFileId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
