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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.TransactionHistory} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.TransactionHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaction-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TransactionHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter projectName;

    private StringFilter khasraNumber;

    private StringFilter state;

    private StringFilter citizenName;

    private StringFilter citizenAadhar;

    private StringFilter surveyerName;

    private StringFilter landValue;

    private DoubleFilter paymentAmount;

    private StringFilter accountNumber;

    private StringFilter bankName;

    private StringFilter transactionId;

    private StringFilter transactionType;

    private StringFilter eventType;

    private StringFilter eventStatus;

    private StringFilter approver1;

    private StringFilter approver2;

    private StringFilter approver3;

    private Boolean distinct;

    public TransactionHistoryCriteria() {}

    public TransactionHistoryCriteria(TransactionHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.projectName = other.projectName == null ? null : other.projectName.copy();
        this.khasraNumber = other.khasraNumber == null ? null : other.khasraNumber.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.citizenName = other.citizenName == null ? null : other.citizenName.copy();
        this.citizenAadhar = other.citizenAadhar == null ? null : other.citizenAadhar.copy();
        this.surveyerName = other.surveyerName == null ? null : other.surveyerName.copy();
        this.landValue = other.landValue == null ? null : other.landValue.copy();
        this.paymentAmount = other.paymentAmount == null ? null : other.paymentAmount.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
        this.transactionType = other.transactionType == null ? null : other.transactionType.copy();
        this.eventType = other.eventType == null ? null : other.eventType.copy();
        this.eventStatus = other.eventStatus == null ? null : other.eventStatus.copy();
        this.approver1 = other.approver1 == null ? null : other.approver1.copy();
        this.approver2 = other.approver2 == null ? null : other.approver2.copy();
        this.approver3 = other.approver3 == null ? null : other.approver3.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransactionHistoryCriteria copy() {
        return new TransactionHistoryCriteria(this);
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

    public StringFilter getProjectName() {
        return projectName;
    }

    public StringFilter projectName() {
        if (projectName == null) {
            projectName = new StringFilter();
        }
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
    }

    public StringFilter getKhasraNumber() {
        return khasraNumber;
    }

    public StringFilter khasraNumber() {
        if (khasraNumber == null) {
            khasraNumber = new StringFilter();
        }
        return khasraNumber;
    }

    public void setKhasraNumber(StringFilter khasraNumber) {
        this.khasraNumber = khasraNumber;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public StringFilter getCitizenName() {
        return citizenName;
    }

    public StringFilter citizenName() {
        if (citizenName == null) {
            citizenName = new StringFilter();
        }
        return citizenName;
    }

    public void setCitizenName(StringFilter citizenName) {
        this.citizenName = citizenName;
    }

    public StringFilter getCitizenAadhar() {
        return citizenAadhar;
    }

    public StringFilter citizenAadhar() {
        if (citizenAadhar == null) {
            citizenAadhar = new StringFilter();
        }
        return citizenAadhar;
    }

    public void setCitizenAadhar(StringFilter citizenAadhar) {
        this.citizenAadhar = citizenAadhar;
    }

    public StringFilter getSurveyerName() {
        return surveyerName;
    }

    public StringFilter surveyerName() {
        if (surveyerName == null) {
            surveyerName = new StringFilter();
        }
        return surveyerName;
    }

    public void setSurveyerName(StringFilter surveyerName) {
        this.surveyerName = surveyerName;
    }

    public StringFilter getLandValue() {
        return landValue;
    }

    public StringFilter landValue() {
        if (landValue == null) {
            landValue = new StringFilter();
        }
        return landValue;
    }

    public void setLandValue(StringFilter landValue) {
        this.landValue = landValue;
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

    public StringFilter getTransactionId() {
        return transactionId;
    }

    public StringFilter transactionId() {
        if (transactionId == null) {
            transactionId = new StringFilter();
        }
        return transactionId;
    }

    public void setTransactionId(StringFilter transactionId) {
        this.transactionId = transactionId;
    }

    public StringFilter getTransactionType() {
        return transactionType;
    }

    public StringFilter transactionType() {
        if (transactionType == null) {
            transactionType = new StringFilter();
        }
        return transactionType;
    }

    public void setTransactionType(StringFilter transactionType) {
        this.transactionType = transactionType;
    }

    public StringFilter getEventType() {
        return eventType;
    }

    public StringFilter eventType() {
        if (eventType == null) {
            eventType = new StringFilter();
        }
        return eventType;
    }

    public void setEventType(StringFilter eventType) {
        this.eventType = eventType;
    }

    public StringFilter getEventStatus() {
        return eventStatus;
    }

    public StringFilter eventStatus() {
        if (eventStatus == null) {
            eventStatus = new StringFilter();
        }
        return eventStatus;
    }

    public void setEventStatus(StringFilter eventStatus) {
        this.eventStatus = eventStatus;
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
        final TransactionHistoryCriteria that = (TransactionHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(projectName, that.projectName) &&
            Objects.equals(khasraNumber, that.khasraNumber) &&
            Objects.equals(state, that.state) &&
            Objects.equals(citizenName, that.citizenName) &&
            Objects.equals(citizenAadhar, that.citizenAadhar) &&
            Objects.equals(surveyerName, that.surveyerName) &&
            Objects.equals(landValue, that.landValue) &&
            Objects.equals(paymentAmount, that.paymentAmount) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(transactionId, that.transactionId) &&
            Objects.equals(transactionType, that.transactionType) &&
            Objects.equals(eventType, that.eventType) &&
            Objects.equals(eventStatus, that.eventStatus) &&
            Objects.equals(approver1, that.approver1) &&
            Objects.equals(approver2, that.approver2) &&
            Objects.equals(approver3, that.approver3) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            projectName,
            khasraNumber,
            state,
            citizenName,
            citizenAadhar,
            surveyerName,
            landValue,
            paymentAmount,
            accountNumber,
            bankName,
            transactionId,
            transactionType,
            eventType,
            eventStatus,
            approver1,
            approver2,
            approver3,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionHistoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (projectName != null ? "projectName=" + projectName + ", " : "") +
            (khasraNumber != null ? "khasraNumber=" + khasraNumber + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (citizenName != null ? "citizenName=" + citizenName + ", " : "") +
            (citizenAadhar != null ? "citizenAadhar=" + citizenAadhar + ", " : "") +
            (surveyerName != null ? "surveyerName=" + surveyerName + ", " : "") +
            (landValue != null ? "landValue=" + landValue + ", " : "") +
            (paymentAmount != null ? "paymentAmount=" + paymentAmount + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (transactionId != null ? "transactionId=" + transactionId + ", " : "") +
            (transactionType != null ? "transactionType=" + transactionType + ", " : "") +
            (eventType != null ? "eventType=" + eventType + ", " : "") +
            (eventStatus != null ? "eventStatus=" + eventStatus + ", " : "") +
            (approver1 != null ? "approver1=" + approver1 + ", " : "") +
            (approver2 != null ? "approver2=" + approver2 + ", " : "") +
            (approver3 != null ? "approver3=" + approver3 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
