package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A PaymentAdvice.
 */
@Entity
@Table(name = "landsys_payment_advice")
public class PaymentAdvice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;

    @NotNull
    @Column(name = "payment_amount", nullable = false)
    private Double paymentAmount;

    @NotNull
    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @Column(name = "check_number")
    private String checkNumber;

    @Column(name = "micr_code")
    private String micrCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_advice_type")
    private PaymentAdviceType paymentAdviceType;

    @Column(name = "reference_number", unique = true)
    private String referenceNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hisssa_type", nullable = false)
    private HissaType hisssaType;

    @JsonIgnoreProperties(value = { "paymentAdvice", "khatedar", "survey", "projectLand" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private LandCompensation landCompensation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "land", "khatedars", "surveys", "landCompensations", "paymentAdvices", "project" }, allowSetters = true)
    private ProjectLand projectLand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentAdvice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    public PaymentAdvice accountHolderName(String accountHolderName) {
        this.setAccountHolderName(accountHolderName);
        return this;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public PaymentAdvice paymentAmount(Double paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBankName() {
        return this.bankName;
    }

    public PaymentAdvice bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public PaymentAdvice accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public PaymentAdvice ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCheckNumber() {
        return this.checkNumber;
    }

    public PaymentAdvice checkNumber(String checkNumber) {
        this.setCheckNumber(checkNumber);
        return this;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getMicrCode() {
        return this.micrCode;
    }

    public PaymentAdvice micrCode(String micrCode) {
        this.setMicrCode(micrCode);
        return this;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public PaymentAdviceType getPaymentAdviceType() {
        return this.paymentAdviceType;
    }

    public PaymentAdvice paymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.setPaymentAdviceType(paymentAdviceType);
        return this;
    }

    public void setPaymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.paymentAdviceType = paymentAdviceType;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public PaymentAdvice referenceNumber(String referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public PaymentAdvice paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public HissaType getHisssaType() {
        return this.hisssaType;
    }

    public PaymentAdvice hisssaType(HissaType hisssaType) {
        this.setHisssaType(hisssaType);
        return this;
    }

    public void setHisssaType(HissaType hisssaType) {
        this.hisssaType = hisssaType;
    }

    public LandCompensation getLandCompensation() {
        return this.landCompensation;
    }

    public void setLandCompensation(LandCompensation landCompensation) {
        this.landCompensation = landCompensation;
    }

    public PaymentAdvice landCompensation(LandCompensation landCompensation) {
        this.setLandCompensation(landCompensation);
        return this;
    }

    public ProjectLand getProjectLand() {
        return this.projectLand;
    }

    public void setProjectLand(ProjectLand projectLand) {
        this.projectLand = projectLand;
    }

    public PaymentAdvice projectLand(ProjectLand projectLand) {
        this.setProjectLand(projectLand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAdvice)) {
            return false;
        }
        return id != null && id.equals(((PaymentAdvice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAdvice{" +
            "id=" + getId() +
            ", accountHolderName='" + getAccountHolderName() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", bankName='" + getBankName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", micrCode='" + getMicrCode() + "'" +
            ", paymentAdviceType='" + getPaymentAdviceType() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", hisssaType='" + getHisssaType() + "'" +
            "}";
    }
}
