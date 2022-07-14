package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Khatedar} entity.
 */
public class KhatedarDTO implements Serializable {

    private Long id;

    @NotNull
    private String caseFileNo;

    @NotNull
    private String remarks;

    private String khatedarStatus;

    private ProjectLandDTO projectLand;

    private CitizenDTO citizen;

    private PaymentAdviceDTO paymentAdvice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseFileNo() {
        return caseFileNo;
    }

    public void setCaseFileNo(String caseFileNo) {
        this.caseFileNo = caseFileNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getKhatedarStatus() {
        return khatedarStatus;
    }

    public void setKhatedarStatus(String khatedarStatus) {
        this.khatedarStatus = khatedarStatus;
    }

    public ProjectLandDTO getProjectLand() {
        return projectLand;
    }

    public void setProjectLand(ProjectLandDTO projectLand) {
        this.projectLand = projectLand;
    }

    public CitizenDTO getCitizen() {
        return citizen;
    }

    public void setCitizen(CitizenDTO citizen) {
        this.citizen = citizen;
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
        if (!(o instanceof KhatedarDTO)) {
            return false;
        }

        KhatedarDTO khatedarDTO = (KhatedarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, khatedarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KhatedarDTO{" +
            "id=" + getId() +
            ", caseFileNo='" + getCaseFileNo() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", khatedarStatus='" + getKhatedarStatus() + "'" +
            ", projectLand=" + getProjectLand() +
            ", citizen=" + getCitizen() +
            ", paymentAdvice=" + getPaymentAdvice() +
            "}";
    }
}
