package com.melontech.landsys.service.dto;

import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.SurveyStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Survey} entity.
 */
public class SurveyDTO implements Serializable {

    private Long id;

    @NotNull
    private String surveyor;

    @NotNull
    private HissaType hissaType;

    @NotNull
    private Double sharePercentage;

    @NotNull
    private Double area;

    @NotNull
    private Double landMarketValue;

    private Double structuralValue;

    private Double horticultureValue;

    private Double forestValue;

    private Double distanceFromCity;

    private String remarks;

    private SurveyStatus status;

    private KhatedarDTO khatedar;

    private ProjectLandDTO projectLand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurveyor() {
        return surveyor;
    }

    public void setSurveyor(String surveyor) {
        this.surveyor = surveyor;
    }

    public HissaType getHissaType() {
        return hissaType;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public Double getSharePercentage() {
        return sharePercentage;
    }

    public void setSharePercentage(Double sharePercentage) {
        this.sharePercentage = sharePercentage;
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

    public Double getStructuralValue() {
        return structuralValue;
    }

    public void setStructuralValue(Double structuralValue) {
        this.structuralValue = structuralValue;
    }

    public Double getHorticultureValue() {
        return horticultureValue;
    }

    public void setHorticultureValue(Double horticultureValue) {
        this.horticultureValue = horticultureValue;
    }

    public Double getForestValue() {
        return forestValue;
    }

    public void setForestValue(Double forestValue) {
        this.forestValue = forestValue;
    }

    public Double getDistanceFromCity() {
        return distanceFromCity;
    }

    public void setDistanceFromCity(Double distanceFromCity) {
        this.distanceFromCity = distanceFromCity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public SurveyStatus getStatus() {
        return status;
    }

    public void setStatus(SurveyStatus status) {
        this.status = status;
    }

    public KhatedarDTO getKhatedar() {
        return khatedar;
    }

    public void setKhatedar(KhatedarDTO khatedar) {
        this.khatedar = khatedar;
    }

    public ProjectLandDTO getProjectLand() {
        return projectLand;
    }

    public void setProjectLand(ProjectLandDTO projectLand) {
        this.projectLand = projectLand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveyDTO)) {
            return false;
        }

        SurveyDTO surveyDTO = (SurveyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, surveyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyDTO{" +
            "id=" + getId() +
            ", surveyor='" + getSurveyor() + "'" +
            ", hissaType='" + getHissaType() + "'" +
            ", sharePercentage=" + getSharePercentage() +
            ", area=" + getArea() +
            ", landMarketValue=" + getLandMarketValue() +
            ", structuralValue=" + getStructuralValue() +
            ", horticultureValue=" + getHorticultureValue() +
            ", forestValue=" + getForestValue() +
            ", distanceFromCity=" + getDistanceFromCity() +
            ", remarks='" + getRemarks() + "'" +
            ", status='" + getStatus() + "'" +
            ", khatedar=" + getKhatedar() +
            ", projectLand=" + getProjectLand() +
            "}";
    }
}
