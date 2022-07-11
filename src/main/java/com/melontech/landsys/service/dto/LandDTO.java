package com.melontech.landsys.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.melontech.landsys.domain.Land} entity.
 */
public class LandDTO implements Serializable {

    private Long id;

    private String ulpin;

    @NotNull
    private String khasraNumber;

    private Double area;

    private Double landMarketValue;

    private Double structuralValue;

    private Double horticultureValue;

    private Double forestValue;

    private String distanceFromCity;

    private Double totalLandValue;

    private StateDTO state;

    private VillageDTO village;

    private UnitDTO unit;

    private LandTypeDTO landType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUlpin() {
        return ulpin;
    }

    public void setUlpin(String ulpin) {
        this.ulpin = ulpin;
    }

    public String getKhasraNumber() {
        return khasraNumber;
    }

    public void setKhasraNumber(String khasraNumber) {
        this.khasraNumber = khasraNumber;
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

    public String getDistanceFromCity() {
        return distanceFromCity;
    }

    public void setDistanceFromCity(String distanceFromCity) {
        this.distanceFromCity = distanceFromCity;
    }

    public Double getTotalLandValue() {
        return totalLandValue;
    }

    public void setTotalLandValue(Double totalLandValue) {
        this.totalLandValue = totalLandValue;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    public VillageDTO getVillage() {
        return village;
    }

    public void setVillage(VillageDTO village) {
        this.village = village;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public LandTypeDTO getLandType() {
        return landType;
    }

    public void setLandType(LandTypeDTO landType) {
        this.landType = landType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandDTO)) {
            return false;
        }

        LandDTO landDTO = (LandDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, landDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandDTO{" +
            "id=" + getId() +
            ", ulpin='" + getUlpin() + "'" +
            ", khasraNumber='" + getKhasraNumber() + "'" +
            ", area=" + getArea() +
            ", landMarketValue=" + getLandMarketValue() +
            ", structuralValue=" + getStructuralValue() +
            ", horticultureValue=" + getHorticultureValue() +
            ", forestValue=" + getForestValue() +
            ", distanceFromCity='" + getDistanceFromCity() + "'" +
            ", totalLandValue=" + getTotalLandValue() +
            ", state=" + getState() +
            ", village=" + getVillage() +
            ", unit=" + getUnit() +
            ", landType=" + getLandType() +
            "}";
    }
}
