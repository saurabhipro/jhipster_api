package com.melontech.landsys.service.criteria;

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
 * Criteria class for the {@link com.melontech.landsys.domain.PaymentFileRecon} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.PaymentFileReconResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-file-recons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PaymentFileReconCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter primaryHolderName;

    private DoubleFilter paymentAmount;

    private LocalDateFilter paymentDate;

    private StringFilter utrNumber;

    private StringFilter referenceNumber;

    private PaymentStatusFilter paymentStatus;

    private LongFilter paymentAdviceId;

    private Boolean distinct;

    public PaymentFileReconCriteria() {}

    public PaymentFileReconCriteria(PaymentFileReconCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.primaryHolderName = other.primaryHolderName == null ? null : other.primaryHolderName.copy();
        this.paymentAmount = other.paymentAmount == null ? null : other.paymentAmount.copy();
        this.paymentDate = other.paymentDate == null ? null : other.paymentDate.copy();
        this.utrNumber = other.utrNumber == null ? null : other.utrNumber.copy();
        this.referenceNumber = other.referenceNumber == null ? null : other.referenceNumber.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.paymentAdviceId = other.paymentAdviceId == null ? null : other.paymentAdviceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentFileReconCriteria copy() {
        return new PaymentFileReconCriteria(this);
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

    public StringFilter getPrimaryHolderName() {
        return primaryHolderName;
    }

    public StringFilter primaryHolderName() {
        if (primaryHolderName == null) {
            primaryHolderName = new StringFilter();
        }
        return primaryHolderName;
    }

    public void setPrimaryHolderName(StringFilter primaryHolderName) {
        this.primaryHolderName = primaryHolderName;
    }

    public DoubleFilter getPaymentAmount() {
        return paymentAmount;
    }

    public DoubleFilter paymentAmount() {
        if (paymentAmount == null) {
            paymentAmount = new DoubleFilter();
        }
        return paymentAmount;
    }

    public void setPaymentAmount(DoubleFilter paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDateFilter getPaymentDate() {
        return paymentDate;
    }

    public LocalDateFilter paymentDate() {
        if (paymentDate == null) {
            paymentDate = new LocalDateFilter();
        }
        return paymentDate;
    }

    public void setPaymentDate(LocalDateFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public StringFilter getUtrNumber() {
        return utrNumber;
    }

    public StringFilter utrNumber() {
        if (utrNumber == null) {
            utrNumber = new StringFilter();
        }
        return utrNumber;
    }

    public void setUtrNumber(StringFilter utrNumber) {
        this.utrNumber = utrNumber;
    }

    public StringFilter getReferenceNumber() {
        return referenceNumber;
    }

    public StringFilter referenceNumber() {
        if (referenceNumber == null) {
            referenceNumber = new StringFilter();
        }
        return referenceNumber;
    }

    public void setReferenceNumber(StringFilter referenceNumber) {
        this.referenceNumber = referenceNumber;
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
        final PaymentFileReconCriteria that = (PaymentFileReconCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(primaryHolderName, that.primaryHolderName) &&
            Objects.equals(paymentAmount, that.paymentAmount) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(utrNumber, that.utrNumber) &&
            Objects.equals(referenceNumber, that.referenceNumber) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(paymentAdviceId, that.paymentAdviceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            primaryHolderName,
            paymentAmount,
            paymentDate,
            utrNumber,
            referenceNumber,
            paymentStatus,
            paymentAdviceId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileReconCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (primaryHolderName != null ? "primaryHolderName=" + primaryHolderName + ", " : "") +
            (paymentAmount != null ? "paymentAmount=" + paymentAmount + ", " : "") +
            (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
            (utrNumber != null ? "utrNumber=" + utrNumber + ", " : "") +
            (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "") +
            (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
            (paymentAdviceId != null ? "paymentAdviceId=" + paymentAdviceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
