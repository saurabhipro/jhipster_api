package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.PaymentFileRecon} entity.
 */
public class PaymentFileReconDTO implements Serializable {

    private Long id;

    @NotNull
    private String primaryHolderName;

    @NotNull
    private Double paymentAmount;

    private LocalDate paymentDate;

    @NotNull
    private String utrNumber;

    private String referenceNumber;

    @NotNull
    private PaymentStatus paymentStatus;

    private PaymentAdviceDTO paymentAdvice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryHolderName() {
        return primaryHolderName;
    }

    public void setPrimaryHolderName(String primaryHolderName) {
        this.primaryHolderName = primaryHolderName;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getUtrNumber() {
        return utrNumber;
    }

    public void setUtrNumber(String utrNumber) {
        this.utrNumber = utrNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentAdviceDTO getPaymentAdvice() {
        return paymentAdvice;
    }

    public void setPaymentAdvice(PaymentAdviceDTO paymentAdvice) {
        this.paymentAdvice = paymentAdvice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentFileReconDTO)) {
            return false;
        }

        PaymentFileReconDTO paymentFileReconDTO = (PaymentFileReconDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentFileReconDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileReconDTO{" +
            "id=" + getId() +
            ", primaryHolderName='" + getPrimaryHolderName() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", utrNumber='" + getUtrNumber() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", paymentAdvice=" + getPaymentAdvice() +
            "}";
    }
}
