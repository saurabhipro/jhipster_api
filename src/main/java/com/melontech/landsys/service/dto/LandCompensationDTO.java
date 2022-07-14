package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.CompensationStatus;
import com.melontech.landsys.domain.enumeration.HissaType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.LandCompensation} entity.
 */
public class LandCompensationDTO implements Serializable {

    private Long id;

    @NotNull
    private HissaType hissaType;

    @NotNull
    private Double area;

    @NotNull
    private Double landMarketValue;

    private Double structuralCompensation;

    private Double horticultureCompensation;

    private Double forestCompensation;

    private Double solatiumMoney;

    private Double additionalCompensation;

    private CompensationStatus status;

    private LocalDate orderDate;

    private Double paymentAmount;

    private String transactionId;

    private ProjectLandDTO projectLand;

    private SurveyDTO survey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HissaType getHissaType() {
        return hissaType;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getLandMarketValue() {
        return landMarketValue;
    }

    public void setLandMarketValue(Double landMarketValue) {
        this.landMarketValue = landMarketValue;
    }

    public Double getStructuralCompensation() {
        return structuralCompensation;
    }

    public void setStructuralCompensation(Double structuralCompensation) {
        this.structuralCompensation = structuralCompensation;
    }

    public Double getHorticultureCompensation() {
        return horticultureCompensation;
    }

    public void setHorticultureCompensation(Double horticultureCompensation) {
        this.horticultureCompensation = horticultureCompensation;
    }

    public Double getForestCompensation() {
        return forestCompensation;
    }

    public void setForestCompensation(Double forestCompensation) {
        this.forestCompensation = forestCompensation;
    }

    public Double getSolatiumMoney() {
        return solatiumMoney;
    }

    public void setSolatiumMoney(Double solatiumMoney) {
        this.solatiumMoney = solatiumMoney;
    }

    public Double getAdditionalCompensation() {
        return additionalCompensation;
    }

    public void setAdditionalCompensation(Double additionalCompensation) {
        this.additionalCompensation = additionalCompensation;
    }

    public CompensationStatus getStatus() {
        return status;
    }

    public void setStatus(CompensationStatus status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public ProjectLandDTO getProjectLand() {
        return projectLand;
    }

    public void setProjectLand(ProjectLandDTO projectLand) {
        this.projectLand = projectLand;
    }

    public SurveyDTO getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyDTO survey) {
        this.survey = survey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandCompensationDTO)) {
            return false;
        }

        LandCompensationDTO landCompensationDTO = (LandCompensationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, landCompensationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandCompensationDTO{" +
            "id=" + getId() +
            ", hissaType='" + getHissaType() + "'" +
            ", area=" + getArea() +
            ", landMarketValue=" + getLandMarketValue() +
            ", structuralCompensation=" + getStructuralCompensation() +
            ", horticultureCompensation=" + getHorticultureCompensation() +
            ", forestCompensation=" + getForestCompensation() +
            ", solatiumMoney=" + getSolatiumMoney() +
            ", additionalCompensation=" + getAdditionalCompensation() +
            ", status='" + getStatus() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", transactionId='" + getTransactionId() + "'" +
            ", projectLand=" + getProjectLand() +
            ", survey=" + getSurvey() +
            "}";
    }
}
