package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.HissaType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.PaymentAdviceDetails} entity.
 */
public class PaymentAdviceDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String landOwners;

    @NotNull
    private HissaType hissaType;

    private PaymentAdviceDTO paymentAdvice;

    private ProjectLandDTO projectLand;

    private KhatedarDTO khatedar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLandOwners() {
        return landOwners;
    }

    public void setLandOwners(String landOwners) {
        this.landOwners = landOwners;
    }

    public HissaType getHissaType() {
        return hissaType;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public PaymentAdviceDTO getPaymentAdvice() {
        return paymentAdvice;
    }

    public void setPaymentAdvice(PaymentAdviceDTO paymentAdvice) {
        this.paymentAdvice = paymentAdvice;
    }

    public ProjectLandDTO getProjectLand() {
        return projectLand;
    }

    public void setProjectLand(ProjectLandDTO projectLand) {
        this.projectLand = projectLand;
    }

    public KhatedarDTO getKhatedar() {
        return khatedar;
    }

    public void setKhatedar(KhatedarDTO khatedar) {
        this.khatedar = khatedar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAdviceDetailsDTO)) {
            return false;
        }

        PaymentAdviceDetailsDTO paymentAdviceDetailsDTO = (PaymentAdviceDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentAdviceDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAdviceDetailsDTO{" +
            "id=" + getId() +
            ", landOwners='" + getLandOwners() + "'" +
            ", hissaType='" + getHissaType() + "'" +
            ", paymentAdvice=" + getPaymentAdvice() +
            ", projectLand=" + getProjectLand() +
            ", khatedar=" + getKhatedar() +
            "}";
    }
}
