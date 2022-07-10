package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.TransactionHistory} entity.
 */
public class TransactionHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String projectName;

    @NotNull
    private String khasraNumber;

    @NotNull
    private String state;

    @NotNull
    private String citizenName;

    @NotNull
    private String citizenAadhar;

    private String surveyerName;

    @NotNull
    private String landValue;

    private Double paymentAmount;

    private String accountNumber;

    private String bankName;

    private String transactionId;

    private String transactionType;

    private String eventType;

    private String eventStatus;

    private String approver1;

    private String approver2;

    private String approver3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getKhasraNumber() {
        return khasraNumber;
    }

    public void setKhasraNumber(String khasraNumber) {
        this.khasraNumber = khasraNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public String getCitizenAadhar() {
        return citizenAadhar;
    }

    public void setCitizenAadhar(String citizenAadhar) {
        this.citizenAadhar = citizenAadhar;
    }

    public String getSurveyerName() {
        return surveyerName;
    }

    public void setSurveyerName(String surveyerName) {
        this.surveyerName = surveyerName;
    }

    public String getLandValue() {
        return landValue;
    }

    public void setLandValue(String landValue) {
        this.landValue = landValue;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
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
        if (!(o instanceof TransactionHistoryDTO)) {
            return false;
        }

        TransactionHistoryDTO transactionHistoryDTO = (TransactionHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionHistoryDTO{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", khasraNumber='" + getKhasraNumber() + "'" +
            ", state='" + getState() + "'" +
            ", citizenName='" + getCitizenName() + "'" +
            ", citizenAadhar='" + getCitizenAadhar() + "'" +
            ", surveyerName='" + getSurveyerName() + "'" +
            ", landValue='" + getLandValue() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", eventStatus='" + getEventStatus() + "'" +
            ", approver1='" + getApprover1() + "'" +
            ", approver2='" + getApprover2() + "'" +
            ", approver3='" + getApprover3() + "'" +
            "}";
    }
}
