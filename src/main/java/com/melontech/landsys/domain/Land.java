package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Land.
 */
@Entity
@Table(name = "landsys_land")
public class Land implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ulpin")
    private String ulpin;

    @NotNull
    @Column(name = "khasra_number", nullable = false)
    private String khasraNumber;

    @Column(name = "area")
    private Double area;

    @Column(name = "land_market_value")
    private Double landMarketValue;

    @Column(name = "structural_value")
    private Double structuralValue;

    @Column(name = "horticulture_value")
    private Double horticultureValue;

    @Column(name = "forest_value")
    private Double forestValue;

    @Column(name = "distance_from_city")
    private String distanceFromCity;

    @Column(name = "total_land_value")
    private Double totalLandValue;

    @JsonIgnoreProperties(value = { "district", "land" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private State state;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "subDistrict", "lands" }, allowSetters = true)
    private Village village;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lands" }, allowSetters = true)
    private Unit unit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "lands" }, allowSetters = true)
    private LandType landType;

    @OneToMany(mappedBy = "land")
    @JsonIgnoreProperties(
        value = { "project", "land", "noticeStatusInfo", "khatedars", "surveys", "landCompensations", "paymentAdvices" },
        allowSetters = true
    )
    private Set<ProjectLand> projectLands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Land id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUlpin() {
        return this.ulpin;
    }

    public Land ulpin(String ulpin) {
        this.setUlpin(ulpin);
        return this;
    }

    public void setUlpin(String ulpin) {
        this.ulpin = ulpin;
    }

    public String getKhasraNumber() {
        return this.khasraNumber;
    }

    public Land khasraNumber(String khasraNumber) {
        this.setKhasraNumber(khasraNumber);
        return this;
    }

    public void setKhasraNumber(String khasraNumber) {
        this.khasraNumber = khasraNumber;
    }

    public Double getArea() {
        return this.area;
    }

    public Land area(Double area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getLandMarketValue() {
        return this.landMarketValue;
    }

    public Land landMarketValue(Double landMarketValue) {
        this.setLandMarketValue(landMarketValue);
        return this;
    }

    public void setLandMarketValue(Double landMarketValue) {
        this.landMarketValue = landMarketValue;
    }

    public Double getStructuralValue() {
        return this.structuralValue;
    }

    public Land structuralValue(Double structuralValue) {
        this.setStructuralValue(structuralValue);
        return this;
    }

    public void setStructuralValue(Double structuralValue) {
        this.structuralValue = structuralValue;
    }

    public Double getHorticultureValue() {
        return this.horticultureValue;
    }

    public Land horticultureValue(Double horticultureValue) {
        this.setHorticultureValue(horticultureValue);
        return this;
    }

    public void setHorticultureValue(Double horticultureValue) {
        this.horticultureValue = horticultureValue;
    }

    public Double getForestValue() {
        return this.forestValue;
    }

    public Land forestValue(Double forestValue) {
        this.setForestValue(forestValue);
        return this;
    }

    public void setForestValue(Double forestValue) {
        this.forestValue = forestValue;
    }

    public String getDistanceFromCity() {
        return this.distanceFromCity;
    }

    public Land distanceFromCity(String distanceFromCity) {
        this.setDistanceFromCity(distanceFromCity);
        return this;
    }

    public void setDistanceFromCity(String distanceFromCity) {
        this.distanceFromCity = distanceFromCity;
    }

    public Double getTotalLandValue() {
        return this.totalLandValue;
    }

    public Land totalLandValue(Double totalLandValue) {
        this.setTotalLandValue(totalLandValue);
        return this;
    }

    public void setTotalLandValue(Double totalLandValue) {
        this.totalLandValue = totalLandValue;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Land state(State state) {
        this.setState(state);
        return this;
    }

    public Village getVillage() {
        return this.village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public Land village(Village village) {
        this.setVillage(village);
        return this;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Land unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public LandType getLandType() {
        return this.landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

    public Land landType(LandType landType) {
        this.setLandType(landType);
        return this;
    }

    public Set<ProjectLand> getProjectLands() {
        return this.projectLands;
    }

    public void setProjectLands(Set<ProjectLand> projectLands) {
        if (this.projectLands != null) {
            this.projectLands.forEach(i -> i.setLand(null));
        }
        if (projectLands != null) {
            projectLands.forEach(i -> i.setLand(this));
        }
        this.projectLands = projectLands;
    }

    public Land projectLands(Set<ProjectLand> projectLands) {
        this.setProjectLands(projectLands);
        return this;
    }

    public Land addProjectLand(ProjectLand projectLand) {
        this.projectLands.add(projectLand);
        projectLand.setLand(this);
        return this;
    }

    public Land removeProjectLand(ProjectLand projectLand) {
        this.projectLands.remove(projectLand);
        projectLand.setLand(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Land)) {
            return false;
        }
        return id != null && id.equals(((Land) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Land{" +
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
            "}";
    }
}
