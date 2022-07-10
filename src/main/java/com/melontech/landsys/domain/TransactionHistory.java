package com.melontech.landsys.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TransactionHistory.
 */
@Entity
@Table(name = "landsys_transaction_history")
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull
    @Column(name = "khasra_number", nullable = false)
    private String khasraNumber;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "citizen_name", nullable = false)
    private String citizenName;

    @NotNull
    @Column(name = "citizen_aadhar", nullable = false)
    private String citizenAadhar;

    @Column(name = "surveyer_name")
    private String surveyerName;

    @NotNull
    @Column(name = "land_value", nullable = false)
    private String landValue;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_status")
    private String eventStatus;

    @Column(name = "approver_1")
    private String approver1;

    @Column(name = "approver_2")
    private String approver2;

    @Column(name = "approver_3")
    private String approver3;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransactionHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public TransactionHistory projectName(String projectName) {
        this.setProjectName(projectName);
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getKhasraNumber() {
        return this.khasraNumber;
    }

    public TransactionHistory khasraNumber(String khasraNumber) {
        this.setKhasraNumber(khasraNumber);
        return this;
    }

    public void setKhasraNumber(String khasraNumber) {
        this.khasraNumber = khasraNumber;
    }

    public String getState() {
        return this.state;
    }

    public TransactionHistory state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCitizenName() {
        return this.citizenName;
    }

    public TransactionHistory citizenName(String citizenName) {
        this.setCitizenName(citizenName);
        return this;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public String getCitizenAadhar() {
        return this.citizenAadhar;
    }

    public TransactionHistory citizenAadhar(String citizenAadhar) {
        this.setCitizenAadhar(citizenAadhar);
        return this;
    }

    public void setCitizenAadhar(String citizenAadhar) {
        this.citizenAadhar = citizenAadhar;
    }

    public String getSurveyerName() {
        return this.surveyerName;
    }

    public TransactionHistory surveyerName(String surveyerName) {
        this.setSurveyerName(surveyerName);
        return this;
    }

    public void setSurveyerName(String surveyerName) {
        this.surveyerName = surveyerName;
    }

    public String getLandValue() {
        return this.landValue;
    }

    public TransactionHistory landValue(String landValue) {
        this.setLandValue(landValue);
        return this;
    }

    public void setLandValue(String landValue) {
        this.landValue = landValue;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public TransactionHistory paymentAmount(Double paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public TransactionHistory accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return this.bankName;
    }

    public TransactionHistory bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public TransactionHistory transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public TransactionHistory transactionType(String transactionType) {
        this.setTransactionType(transactionType);
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getEventType() {
        return this.eventType;
    }

    public TransactionHistory eventType(String eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventStatus() {
        return this.eventStatus;
    }

    public TransactionHistory eventStatus(String eventStatus) {
        this.setEventStatus(eventStatus);
        return this;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getApprover1() {
        return this.approver1;
    }

    public TransactionHistory approver1(String approver1) {
        this.setApprover1(approver1);
        return this;
    }

    public void setApprover1(String approver1) {
        this.approver1 = approver1;
    }

    public String getApprover2() {
        return this.approver2;
    }

    public TransactionHistory approver2(String approver2) {
        this.setApprover2(approver2);
        return this;
    }

    public void setApprover2(String approver2) {
        this.approver2 = approver2;
    }

    public String getApprover3() {
        return this.approver3;
    }

    public TransactionHistory approver3(String approver3) {
        this.setApprover3(approver3);
        return this;
    }

    public void setApprover3(String approver3) {
        this.approver3 = approver3;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionHistory)) {
            return false;
        }
        return id != null && id.equals(((TransactionHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionHistory{" +
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
