package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.PaymentFile} entity.
 */
public class PaymentFileDTO implements Serializable {

    private Long id;

    @NotNull
    private Double paymentFileId;

    @NotNull
    private Double totalPaymentAmount;

    private LocalDate paymentFileDate;

    @NotNull
    private PaymentStatus paymentStatus;

    private String bankName;

    private String ifscCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPaymentFileId() {
        return paymentFileId;
    }

    public void setPaymentFileId(Double paymentFileId) {
        this.paymentFileId = paymentFileId;
    }

    public Double getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(Double totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public LocalDate getPaymentFileDate() {
        return paymentFileDate;
    }

    public void setPaymentFileDate(LocalDate paymentFileDate) {
        this.paymentFileDate = paymentFileDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentFileDTO)) {
            return false;
        }

        PaymentFileDTO paymentFileDTO = (PaymentFileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentFileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileDTO{" +
            "id=" + getId() +
            ", paymentFileId=" + getPaymentFileId() +
            ", totalPaymentAmount=" + getTotalPaymentAmount() +
            ", paymentFileDate='" + getPaymentFileDate() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            "}";
    }
}
