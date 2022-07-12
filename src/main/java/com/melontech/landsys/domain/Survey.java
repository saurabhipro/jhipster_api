package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.SurveyStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Survey.
 */
@Entity
@Table(name = "landsys_survey")
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "surveyor", nullable = false)
    private String surveyor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hissa_type", nullable = false)
    private HissaType hissaType;

    @NotNull
    @Column(name = "share_percentage", nullable = false)
    private Double sharePercentage;

    @NotNull
    @Column(name = "area", nullable = false)
    private Double area;

    @NotNull
    @Column(name = "land_market_value", nullable = false)
    private Double landMarketValue;

    @Column(name = "structural_value")
    private Double structuralValue;

    @Column(name = "horticulture_value")
    private Double horticultureValue;

    @Column(name = "forest_value")
    private Double forestValue;

    @Column(name = "distance_from_city")
    private Double distanceFromCity;

    @Column(name = "remarks")
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SurveyStatus status;

    @JsonIgnoreProperties(
        value = {
            "land", "project", "citizen", "noticeStatusInfo", "survey", "landCompensation", "paymentAdvices", "paymentAdviceDetails",
        },
        allowSetters = true
    )
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private ProjectLand projectLand;

    @JsonIgnoreProperties(value = { "projectLand", "survey", "paymentAdvices" }, allowSetters = true)
    @OneToOne(mappedBy = "survey")
    private LandCompensation landCompensation;

    @OneToMany(mappedBy = "survey")
    @JsonIgnoreProperties(
        value = {
            "landCompensation", "projectLand", "survey", "citizen", "paymentFile", "paymentFileRecon", "land", "paymentAdviceDetails",
        },
        allowSetters = true
    )
    private Set<PaymentAdvice> paymentAdvices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Survey id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurveyor() {
        return this.surveyor;
    }

    public Survey surveyor(String surveyor) {
        this.setSurveyor(surveyor);
        return this;
    }

    public void setSurveyor(String surveyor) {
        this.surveyor = surveyor;
    }

    public HissaType getHissaType() {
        return this.hissaType;
    }

    public Survey hissaType(HissaType hissaType) {
        this.setHissaType(hissaType);
        return this;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public Double getSharePercentage() {
        return this.sharePercentage;
    }

    public Survey sharePercentage(Double sharePercentage) {
        this.setSharePercentage(sharePercentage);
        return this;
    }

    public void setSharePercentage(Double sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    public Double getArea() {
        return this.area;
    }

    public Survey area(Double area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getLandMarketValue() {
        return this.landMarketValue;
    }

    public Survey landMarketValue(Double landMarketValue) {
        this.setLandMarketValue(landMarketValue);
        return this;
    }

    public void setLandMarketValue(Double landMarketValue) {
        this.landMarketValue = landMarketValue;
    }

    public Double getStructuralValue() {
        return this.structuralValue;
    }

    public Survey structuralValue(Double structuralValue) {
        this.setStructuralValue(structuralValue);
        return this;
    }

    public void setStructuralValue(Double structuralValue) {
        this.structuralValue = structuralValue;
    }

    public Double getHorticultureValue() {
        return this.horticultureValue;
    }

    public Survey horticultureValue(Double horticultureValue) {
        this.setHorticultureValue(horticultureValue);
        return this;
    }

    public void setHorticultureValue(Double horticultureValue) {
        this.horticultureValue = horticultureValue;
    }

    public Double getForestValue() {
        return this.forestValue;
    }

    public Survey forestValue(Double forestValue) {
        this.setForestValue(forestValue);
        return this;
    }

    public void setForestValue(Double forestValue) {
        this.forestValue = forestValue;
    }

    public Double getDistanceFromCity() {
        return this.distanceFromCity;
    }

    public Survey distanceFromCity(Double distanceFromCity) {
        this.setDistanceFromCity(distanceFromCity);
        return this;
    }

    public void setDistanceFromCity(Double distanceFromCity) {
        this.distanceFromCity = distanceFromCity;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public Survey remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public SurveyStatus getStatus() {
        return this.status;
    }

    public Survey status(SurveyStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SurveyStatus status) {
        this.status = status;
    }

    public ProjectLand getProjectLand() {
        return this.projectLand;
    }

    public void setProjectLand(ProjectLand projectLand) {
        this.projectLand = projectLand;
    }

    public Survey projectLand(ProjectLand projectLand) {
        this.setProjectLand(projectLand);
        return this;
    }

    public LandCompensation getLandCompensation() {
        return this.landCompensation;
    }

    public void setLandCompensation(LandCompensation landCompensation) {
        if (this.landCompensation != null) {
            this.landCompensation.setSurvey(null);
        }
        if (landCompensation != null) {
            landCompensation.setSurvey(this);
        }
        this.landCompensation = landCompensation;
    }

    public Survey landCompensation(LandCompensation landCompensation) {
        this.setLandCompensation(landCompensation);
        return this;
    }

    public Set<PaymentAdvice> getPaymentAdvices() {
        return this.paymentAdvices;
    }

    public void setPaymentAdvices(Set<PaymentAdvice> paymentAdvices) {
        if (this.paymentAdvices != null) {
            this.paymentAdvices.forEach(i -> i.setSurvey(null));
        }
        if (paymentAdvices != null) {
            paymentAdvices.forEach(i -> i.setSurvey(this));
        }
        this.paymentAdvices = paymentAdvices;
    }

    public Survey paymentAdvices(Set<PaymentAdvice> paymentAdvices) {
        this.setPaymentAdvices(paymentAdvices);
        return this;
    }

    public Survey addPaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvices.add(paymentAdvice);
        paymentAdvice.setSurvey(this);
        return this;
    }

    public Survey removePaymentAdvice(PaymentAdvice paymentAdvice) {
        this.paymentAdvices.remove(paymentAdvice);
        paymentAdvice.setSurvey(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Survey)) {
            return false;
        }
        return id != null && id.equals(((Survey) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Survey{" +
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
            "}";
    }
}
