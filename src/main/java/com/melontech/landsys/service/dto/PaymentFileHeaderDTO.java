package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.PaymentAdviceType;
import com.melontech.landsys.domain.enumeration.PaymentStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.PaymentFileHeader} entity.
 */
public class PaymentFileHeaderDTO implements Serializable {

    private Long id;

    @NotNull
    private Double grandTotalPaymentAmount;

    @NotNull
    private PaymentStatus paymentStatus;

    private PaymentAdviceType paymentMode;

    private String approverRemarks;

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrandTotalPaymentAmount() {
        return grandTotalPaymentAmount;
    }

    public void setGrandTotalPaymentAmount(Double grandTotalPaymentAmount) {
        this.grandTotalPaymentAmount = grandTotalPaymentAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentAdviceType getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentAdviceType paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getApproverRemarks() {
        return approverRemarks;
    }

    public void setApproverRemarks(String approverRemarks) {
        this.approverRemarks = approverRemarks;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentFileHeaderDTO)) {
            return false;
        }

        PaymentFileHeaderDTO paymentFileHeaderDTO = (PaymentFileHeaderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentFileHeaderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentFileHeaderDTO{" +
            "id=" + getId() +
            ", grandTotalPaymentAmount=" + getGrandTotalPaymentAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", approverRemarks='" + getApproverRemarks() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
