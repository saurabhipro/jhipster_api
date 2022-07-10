package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.HissaType;
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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.PaymentAdvice} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.PaymentAdviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-advices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PaymentAdviceCriteria implements Serializable, Criteria {

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
     * Class for filtering HissaType
     */
    public static class HissaTypeFilter extends Filter<HissaType> {

        public HissaTypeFilter() {}

        public HissaTypeFilter(HissaTypeFilter filter) {
            super(filter);
        }

        @Override
        public HissaTypeFilter copy() {
            return new HissaTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter accountHolderName;

    private DoubleFilter paymentAmount;

    private StringFilter bankName;

    private StringFilter accountNumber;

    private StringFilter ifscCode;

    private StringFilter checkNumber;

    private StringFilter micrCode;

    private PaymentAdviceTypeFilter paymentAdviceType;

    private StringFilter referenceNumber;

    private PaymentStatusFilter paymentStatus;

    private HissaTypeFilter hisssaType;

    private LongFilter landCompensationId;

    private LongFilter projectLandId;

    private Boolean distinct;

    public PaymentAdviceCriteria() {}

    public PaymentAdviceCriteria(PaymentAdviceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountHolderName = other.accountHolderName == null ? null : other.accountHolderName.copy();
        this.paymentAmount = other.paymentAmount == null ? null : other.paymentAmount.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.ifscCode = other.ifscCode == null ? null : other.ifscCode.copy();
        this.checkNumber = other.checkNumber == null ? null : other.checkNumber.copy();
        this.micrCode = other.micrCode == null ? null : other.micrCode.copy();
        this.paymentAdviceType = other.paymentAdviceType == null ? null : other.paymentAdviceType.copy();
        this.referenceNumber = other.referenceNumber == null ? null : other.referenceNumber.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.hisssaType = other.hisssaType == null ? null : other.hisssaType.copy();
        this.landCompensationId = other.landCompensationId == null ? null : other.landCompensationId.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentAdviceCriteria copy() {
        return new PaymentAdviceCriteria(this);
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

    public StringFilter getAccountHolderName() {
        return accountHolderName;
    }

    public StringFilter accountHolderName() {
        if (accountHolderName == null) {
            accountHolderName = new StringFilter();
        }
        return accountHolderName;
    }

    public void setAccountHolderName(StringFilter accountHolderName) {
        this.accountHolderName = accountHolderName;
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

    public StringFilter getAccountNumber() {
        return accountNumber;
    }

    public StringFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new StringFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(StringFilter accountNumber) {
        this.accountNumber = accountNumber;
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

    public StringFilter getCheckNumber() {
        return checkNumber;
    }

    public StringFilter checkNumber() {
        if (checkNumber == null) {
            checkNumber = new StringFilter();
        }
        return checkNumber;
    }

    public void setCheckNumber(StringFilter checkNumber) {
        this.checkNumber = checkNumber;
    }

    public StringFilter getMicrCode() {
        return micrCode;
    }

    public StringFilter micrCode() {
        if (micrCode == null) {
            micrCode = new StringFilter();
        }
        return micrCode;
    }

    public void setMicrCode(StringFilter micrCode) {
        this.micrCode = micrCode;
    }

    public PaymentAdviceTypeFilter getPaymentAdviceType() {
        return paymentAdviceType;
    }

    public PaymentAdviceTypeFilter paymentAdviceType() {
        if (paymentAdviceType == null) {
            paymentAdviceType = new PaymentAdviceTypeFilter();
        }
        return paymentAdviceType;
    }

    public void setPaymentAdviceType(PaymentAdviceTypeFilter paymentAdviceType) {
        this.paymentAdviceType = paymentAdviceType;
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

    public HissaTypeFilter getHisssaType() {
        return hisssaType;
    }

    public HissaTypeFilter hisssaType() {
        if (hisssaType == null) {
            hisssaType = new HissaTypeFilter();
        }
        return hisssaType;
    }

    public void setHisssaType(HissaTypeFilter hisssaType) {
        this.hisssaType = hisssaType;
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
        final PaymentAdviceCriteria that = (PaymentAdviceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accountHolderName, that.accountHolderName) &&
            Objects.equals(paymentAmount, that.paymentAmount) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(checkNumber, that.checkNumber) &&
            Objects.equals(micrCode, that.micrCode) &&
            Objects.equals(paymentAdviceType, that.paymentAdviceType) &&
            Objects.equals(referenceNumber, that.referenceNumber) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(hisssaType, that.hisssaType) &&
            Objects.equals(landCompensationId, that.landCompensationId) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            accountHolderName,
            paymentAmount,
            bankName,
            accountNumber,
            ifscCode,
            checkNumber,
            micrCode,
            paymentAdviceType,
            referenceNumber,
            paymentStatus,
            hisssaType,
            landCompensationId,
            projectLandId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAdviceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (accountHolderName != null ? "accountHolderName=" + accountHolderName + ", " : "") +
            (paymentAmount != null ? "paymentAmount=" + paymentAmount + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
            (checkNumber != null ? "checkNumber=" + checkNumber + ", " : "") +
            (micrCode != null ? "micrCode=" + micrCode + ", " : "") +
            (paymentAdviceType != null ? "paymentAdviceType=" + paymentAdviceType + ", " : "") +
            (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "") +
            (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
            (hisssaType != null ? "hisssaType=" + hisssaType + ", " : "") +
            (landCompensationId != null ? "landCompensationId=" + landCompensationId + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}