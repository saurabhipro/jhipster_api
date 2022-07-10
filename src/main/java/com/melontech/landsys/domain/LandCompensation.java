package com.melontech.landsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.melontech.landsys.domain.enumeration.HissaType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LandCompensation.
 */
@Entity
@Table(name = "land_compensation")
public class LandCompensation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hissa_type", nullable = false)
    private HissaType hissaType;

    @NotNull
    @Column(name = "area", nullable = false)
    private Double area;

    @NotNull
    @Column(name = "share_percentage", nullable = false)
    private Integer sharePercentage;

    @NotNull
    @Column(name = "land_market_value", nullable = false)
    private Double landMarketValue;

    @Column(name = "structural_compensation")
    private Double structuralCompensation;

    @Column(name = "horticulture_compensation")
    private Double horticultureCompensation;

    @Column(name = "forest_compensation")
    private Double forestCompensation;

    @Column(name = "solatium_money")
    private Double solatiumMoney;

    @Column(name = "additional_compensation")
    private Double additionalCompensation;

    @Column(name = "status")
    private String status;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "transaction_id")
    private String transactionId;

    @JsonIgnoreProperties(value = { "landCompensation", "projectLand" }, allowSetters = true)
    @OneToOne(mappedBy = "landCompensation")
    private PaymentAdvice paymentAdvice;

    @ManyToOne
    @JsonIgnoreProperties(value = { "surveys", "landCompensations", "citizen", "projectLand" }, allowSetters = true)
    private Khatedar khatedar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "landCompensations", "khatedar", "projectLand" }, allowSetters = true)
    private Survey survey;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "land", "khatedars", "surveys", "landCompensations", "paymentAdvices", "project" }, allowSetters = true)
    private ProjectLand projectLand;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LandCompensation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HissaType getHissaType() {
        return this.hissaType;
    }

    public LandCompensation hissaType(HissaType hissaType) {
        this.setHissaType(hissaType);
        return this;
    }

    public void setHissaType(HissaType hissaType) {
        this.hissaType = hissaType;
    }

    public Double getArea() {
        return this.area;
    }

    public LandCompensation area(Double area) {
        this.setArea(area);
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getSharePercentage() {
        return this.sharePercentage;
    }

    public LandCompensation sharePercentage(Integer sharePercentage) {
        this.setSharePercentage(sharePercentage);
        return this;
    }

    public void setSharePercentage(Integer sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    public Double getLandMarketValue() {
        return this.landMarketValue;
    }

    public LandCompensation landMarketValue(Double landMarketValue) {
        this.setLandMarketValue(landMarketValue);
        return this;
    }

    public void setLandMarketValue(Double landMarketValue) {
        this.landMarketValue = landMarketValue;
    }

    public Double getStructuralCompensation() {
        return this.structuralCompensation;
    }

    public LandCompensation structuralCompensation(Double structuralCompensation) {
        this.setStructuralCompensation(structuralCompensation);
        return this;
    }

    public void setStructuralCompensation(Double structuralCompensation) {
        this.structuralCompensation = structuralCompensation;
    }

    public Double getHorticultureCompensation() {
        return this.horticultureCompensation;
    }

    public LandCompensation horticultureCompensation(Double horticultureCompensation) {
        this.setHorticultureCompensation(horticultureCompensation);
        return this;
    }

    public void setHorticultureCompensation(Double horticultureCompensation) {
        this.horticultureCompensation = horticultureCompensation;
    }

    public Double getForestCompensation() {
        return this.forestCompensation;
    }

    public LandCompensation forestCompensation(Double forestCompensation) {
        this.setForestCompensation(forestCompensation);
        return this;
    }

    public void setForestCompensation(Double forestCompensation) {
        this.forestCompensation = forestCompensation;
    }

    public Double getSolatiumMoney() {
        return this.solatiumMoney;
    }

    public LandCompensation solatiumMoney(Double solatiumMoney) {
        this.setSolatiumMoney(solatiumMoney);
        return this;
    }

    public void setSolatiumMoney(Double solatiumMoney) {
        this.solatiumMoney = solatiumMoney;
    }

    public Double getAdditionalCompensation() {
        return this.additionalCompensation;
    }

    public LandCompensation additionalCompensation(Double additionalCompensation) {
        this.setAdditionalCompensation(additionalCompensation);
        return this;
    }

    public void setAdditionalCompensation(Double additionalCompensation) {
        this.additionalCompensation = additionalCompensation;
    }

    public String getStatus() {
        return this.status;
    }

    public LandCompensation status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getOrderDate() {
        return this.orderDate;
    }

    public LandCompensation orderDate(Instant orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getPaymentDate() {
        return this.paymentDate;
    }

    public LandCompensation paymentDate(Instant paymentDate) {
        this.setPaymentDate(paymentDate);
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return this.paymentAmount;
    }

    public LandCompensation paymentAmount(Double paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public LandCompensation transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentAdvice getPaymentAdvice() {
        return this.paymentAdvice;
    }

    public void setPaymentAdvice(PaymentAdvice paymentAdvice) {
        if (this.paymentAdvice != null) {
            this.paymentAdvice.setLandCompensation(null);
        }
        if (paymentAdvice != null) {
            paymentAdvice.setLandCompensation(this);
        }
        this.paymentAdvice = paymentAdvice;
    }

    public LandCompensation paymentAdvice(PaymentAdvice paymentAdvice) {
        this.setPaymentAdvice(paymentAdvice);
        return this;
    }

    public Khatedar getKhatedar() {
        return this.khatedar;
    }

    public void setKhatedar(Khatedar khatedar) {
        this.khatedar = khatedar;
    }

    public LandCompensation khatedar(Khatedar khatedar) {
        this.setKhatedar(khatedar);
        return this;
    }

    public Survey getSurvey() {
        return this.survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public LandCompensation survey(Survey survey) {
        this.setSurvey(survey);
        return this;
    }

    public ProjectLand getProjectLand() {
        return this.projectLand;
    }

    public void setProjectLand(ProjectLand projectLand) {
        this.projectLand = projectLand;
    }

    public LandCompensation projectLand(ProjectLand projectLand) {
        this.setProjectLand(projectLand);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LandCompensation)) {
            return false;
        }
        return id != null && id.equals(((LandCompensation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandCompensation{" +
            "id=" + getId() +
            ", hissaType='" + getHissaType() + "'" +
            ", area=" + getArea() +
            ", sharePercentage=" + getSharePercentage() +
            ", landMarketValue=" + getLandMarketValue() +
            ", structuralCompensation=" + getStructuralCompensation() +
            ", horticultureCompensation=" + getHorticultureCompensation() +
            ", forestCompensation=" + getForestCompensation() +
            ", solatiumMoney=" + getSolatiumMoney() +
            ", additionalCompensation=" + getAdditionalCompensation() +
            ", status='" + getStatus() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", transactionId='" + getTransactionId() + "'" +
            "}";
    }
}
