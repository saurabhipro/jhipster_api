package com.melontech.landsys.domain;

import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CreatePaymentFile.
 */
@Entity
@Table(name = "landsys_create_payment_file")
public class CreatePaymentFile implements Serializable {

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
    @Column(name = "account_holder_bank_name", nullable = false)
    private String accountHolderBankName;

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

    @Column(name = "reference_number")
    private UUID referenceNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hissa_type", nullable = false)
    private HissaType hissaType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CreatePaymentFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return this.accountHolderName;
    }

    public CreatePaymentFile accountHolderName(String accountHolderName) {
        this.setAccountHolderName(accountHolderName);
        return this;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderBankName() {
        return this.accountHolderBankName;
    }

    public CreatePaymentFile accountHolderBankName(String accountHolderBankName) {
        this.setAccountHolderBankName(accountHolderBankName);
        return this;
    }

    public void setAccountHolderBankName(String accountHolderBankName) {
        this.accountHolderBankName = accountHolderBankName;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public CreatePaymentFile paymentAmount(Double paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBankName() {
        return this.bankName;
    }

    public CreatePaymentFile bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public CreatePaymentFile accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public CreatePaymentFile ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCheckNumber() {
        return this.checkNumber;
    }

    public CreatePaymentFile checkNumber(String checkNumber) {
        this.setCheckNumber(checkNumber);
        return this;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getMicrCode() {
        return this.micrCode;
    }

    public CreatePaymentFile micrCode(String micrCode) {
        this.setMicrCode(micrCode);
        return this;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public PaymentAdviceType getPaymentAdviceType() {
        return this.paymentAdviceType;
    }

    public CreatePaymentFile paymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.setPaymentAdviceType(paymentAdviceType);
        return this;
    }

    public void setPaymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.paymentAdviceType = paymentAdviceType;
    }

    public UUID getReferenceNumber() {
        return this.referenceNumber;
    }

    public CreatePaymentFile referenceNumber(UUID referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public void setReferenceNumber(UUID referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public CreatePaymentFile paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public HissaType getHissaType() {
        return this.hissaType;
    }

    public CreatePaymentFile hissaType(HissaType hissaType) {
        this.setHissaType(hissaType);
        return this;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreatePaymentFile)) {
            return false;
        }
        return id != null && id.equals(((CreatePaymentFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreatePaymentFile{" +
            "id=" + getId() +
            ", accountHolderName='" + getAccountHolderName() + "'" +
            ", accountHolderBankName='" + getAccountHolderBankName() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", bankName='" + getBankName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", checkNumber='" + getCheckNumber() + "'" +
            ", micrCode='" + getMicrCode() + "'" +
            ", paymentAdviceType='" + getPaymentAdviceType() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", hissaType='" + getHissaType() + "'" +
            "}";
    }
}
