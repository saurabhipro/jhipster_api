package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.CreatePaymentFile} entity.
 */
public class CreatePaymentFileDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountHolderName;

    @NotNull
    private String accountHolderBankName;

    @NotNull
    private Double paymentAmount;

    @NotNull
    private String bankName;

    @NotNull
    private String accountNumber;

    @NotNull
    private String ifscCode;

    private String checkNumber;

    private String micrCode;

    private PaymentAdviceType paymentAdviceType;

    private UUID referenceNumber;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private HissaType hissaType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderBankName() {
        return accountHolderBankName;
    }

    public void setAccountHolderBankName(String accountHolderBankName) {
        this.accountHolderBankName = accountHolderBankName;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public PaymentAdviceType getPaymentAdviceType() {
        return paymentAdviceType;
    }

    public void setPaymentAdviceType(PaymentAdviceType paymentAdviceType) {
        this.paymentAdviceType = paymentAdviceType;
    }

    public UUID getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(UUID referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public HissaType getHissaType() {
        return hissaType;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreatePaymentFileDTO)) {
            return false;
        }

        CreatePaymentFileDTO createPaymentFileDTO = (CreatePaymentFileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, createPaymentFileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreatePaymentFileDTO{" +
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
