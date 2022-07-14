package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
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
 * Criteria class for the {@link com.melontech.landsys.domain.PaymentFile} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.PaymentFileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PaymentFileCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PaymentStatus
     */
    public static class PaymentStatusFilter extends Filter<PaymentStatus> {

        public PaymentStatusFilter() {}

        public PaymentStatusFilter(PaymentStatusFilter filter) {
            super(filter);
        }

        @Override
        public PaymentStatusFilter copy() {
            return new PaymentStatusFilter(this);
        }
    }

    /**
     * Class for filtering PaymentAdviceType
     */
    public static class PaymentAdviceTypeFilter extends Filter<PaymentAdviceType> {

        public PaymentAdviceTypeFilter() {}

        public PaymentAdviceTypeFilter(PaymentAdviceTypeFilter filter) {
            super(filter);
        }

        @Override
        public PaymentAdviceTypeFilter copy() {
            return new PaymentAdviceTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter paymentFileId;

    private DoubleFilter totalPaymentAmount;

    private LocalDateFilter paymentFileDate;

    private PaymentStatusFilter paymentStatus;

    private StringFilter bankName;

    private StringFilter ifscCode;

    private PaymentAdviceTypeFilter paymentMode;

    private LongFilter khatedarId;

    private LongFilter paymentAdviceId;

    private LongFilter projectLandId;

    private LongFilter surveyId;

    private LongFilter bankId;

    private LongFilter bankBranchId;

    private LongFilter landCompensationId;

    private Boolean distinct;

    public PaymentFileCriteria() {}

    public PaymentFileCriteria(PaymentFileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.paymentFileId = other.paymentFileId == null ? null : other.paymentFileId.copy();
        this.totalPaymentAmount = other.totalPaymentAmount == null ? null : other.totalPaymentAmount.copy();
        this.paymentFileDate = other.paymentFileDate == null ? null : other.paymentFileDate.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.ifscCode = other.ifscCode == null ? null : other.ifscCode.copy();
        this.paymentMode = other.paymentMode == null ? null : other.paymentMode.copy();
        this.khatedarId = other.khatedarId == null ? null : other.khatedarId.copy();
        this.paymentAdviceId = other.paymentAdviceId == null ? null : other.paymentAdviceId.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.surveyId = other.surveyId == null ? null : other.surveyId.copy();
        this.bankId = other.bankId == null ? null : other.bankId.copy();
        this.bankBranchId = other.bankBranchId == null ? null : other.bankBranchId.copy();
        this.landCompensationId = other.landCompensationId == null ? null : other.landCompensationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentFileCriteria copy() {
        return new PaymentFileCriteria(this);
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

    public DoubleFilter getPaymentFileId() {
        return paymentFileId;
    }

    public DoubleFilter paymentFileId() {
        if (paymentFileId == null) {
            paymentFileId = new DoubleFilter();
        }
        return paymentFileId;
    }

    public void setPaymentFileId(DoubleFilter paymentFileId) {
        this.paymentFileId = paymentFileId;
    }

    public DoubleFilter getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public DoubleFilter totalPaymentAmount() {
        if (totalPaymentAmount == null) {
            totalPaymentAmount = new DoubleFilter();
        }
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(DoubleFilter totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public LocalDateFilter getPaymentFileDate() {
        return paymentFileDate;
    }

    public LocalDateFilter paymentFileDate() {
        if (paymentFileDate == null) {
            paymentFileDate = new LocalDateFilter();
        }
        return paymentFileDate;
    }

    public void setPaymentFileDate(LocalDateFilter paymentFileDate) {
        this.paymentFileDate = paymentFileDate;
    }

    public PaymentStatusFilter getPaymentStatus() {
        return paymentStatus;
    }

    public PaymentStatusFilter paymentStatus() {
        if (paymentStatus == null) {
            paymentStatus = new PaymentStatusFilter();
        }
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getIfscCode() {
        return ifscCode;
    }

    public StringFilter ifscCode() {
        if (ifscCode == null) {
            ifscCode = new StringFilter();
        }
        return ifscCode;
    }

    public void setIfscCode(StringFilter ifscCode) {
        this.ifscCode = ifscCode;
    }

    public PaymentAdviceTypeFilter getPaymentMode() {
        return paymentMode;
    }

    public PaymentAdviceTypeFilter paymentMode() {
        if (paymentMode == null) {
            paymentMode = new PaymentAdviceTypeFilter();
        }
        return paymentMode;
    }

    public void setPaymentMode(PaymentAdviceTypeFilter paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LongFilter getKhatedarId() {
        return khatedarId;
    }

    public LongFilter khatedarId() {
        if (khatedarId == null) {
            khatedarId = new LongFilter();
        }
        return khatedarId;
    }

    public void setKhatedarId(LongFilter khatedarId) {
        this.khatedarId = khatedarId;
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

    public LongFilter getBankId() {
        return bankId;
    }

    public LongFilter bankId() {
        if (bankId == null) {
            bankId = new LongFilter();
        }
        return bankId;
    }

    public void setBankId(LongFilter bankId) {
        this.bankId = bankId;
    }

    public LongFilter getBankBranchId() {
        return bankBranchId;
    }

    public LongFilter bankBranchId() {
        if (bankBranchId == null) {
            bankBranchId = new LongFilter();
        }
        return bankBranchId;
    }

    public void setBankBranchId(LongFilter bankBranchId) {
        this.bankBranchId = bankBranchId;
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
        final PaymentFileCriteria that = (PaymentFileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(paymentFileId, that.paymentFileId) &&
            Objects.equals(totalPaymentAmount, that.totalPaymentAmount) &&
            Objects.equals(paymentFileDate, that.paymentFileDate) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(paymentMode, that.paymentMode) &&
            Objects.equals(khatedarId, that.khatedarId) &&
            Objects.equals(paymentAdviceId, that.paymentAdviceId) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(surveyId, that.surveyId) &&
            Objects.equals(bankId, that.bankId) &&
            Objects.equals(bankBranchId, that.bankBranchId) &&
            Objects.equals(landCompensationId, that.landCompensationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            paymentFileId,
            totalPaymentAmount,
            paymentFileDate,
            paymentStatus,
            bankName,
            ifscCode,
            paymentMode,
            khatedarId,
            paymentAdviceId,
            projectLandId,
            surveyId,
            bankId,
            bankBranchId,
            landCompensationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (paymentFileId != null ? "paymentFileId=" + paymentFileId + ", " : "") +
            (totalPaymentAmount != null ? "totalPaymentAmount=" + totalPaymentAmount + ", " : "") +
            (paymentFileDate != null ? "paymentFileDate=" + paymentFileDate + ", " : "") +
            (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
            (paymentMode != null ? "paymentMode=" + paymentMode + ", " : "") +
            (khatedarId != null ? "khatedarId=" + khatedarId + ", " : "") +
            (paymentAdviceId != null ? "paymentAdviceId=" + paymentAdviceId + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (surveyId != null ? "surveyId=" + surveyId + ", " : "") +
            (bankId != null ? "bankId=" + bankId + ", " : "") +
            (bankBranchId != null ? "bankBranchId=" + bankBranchId + ", " : "") +
            (landCompensationId != null ? "landCompensationId=" + landCompensationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
