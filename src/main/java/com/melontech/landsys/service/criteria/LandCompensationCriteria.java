package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.CompensationStatus;
import com.melontech.landsys.domain.enumeration.HissaType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.LandCompensation} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.LandCompensationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /land-compensations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class LandCompensationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering HissaType
     */
    public static class HissaTypeFilter extends Filter<HissaType> {

        public HissaTypeFilter() {}

        public HissaTypeFilter(HissaTypeFilter filter) {
            super(filter);
        }

        @Override
        public HissaTypeFilter copy() {
            return new HissaTypeFilter(this);
        }
    }

    /**
     * Class for filtering CompensationStatus
     */
    public static class CompensationStatusFilter extends Filter<CompensationStatus> {

        public CompensationStatusFilter() {}

        public CompensationStatusFilter(CompensationStatusFilter filter) {
            super(filter);
        }

        @Override
        public CompensationStatusFilter copy() {
            return new CompensationStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private HissaTypeFilter hissaType;

    private DoubleFilter area;

    private DoubleFilter sharePercentage;

    private DoubleFilter landMarketValue;

    private DoubleFilter structuralCompensation;

    private DoubleFilter horticultureCompensation;

    private DoubleFilter forestCompensation;

    private DoubleFilter solatiumMoney;

    private DoubleFilter additionalCompensation;

    private CompensationStatusFilter status;

    private LocalDateFilter orderDate;

    private DoubleFilter paymentAmount;

    private StringFilter transactionId;

    private LongFilter khatedarId;

    private LongFilter surveyId;

    private LongFilter projectLandId;

    private LongFilter paymentAdviceId;

    private Boolean distinct;

    public LandCompensationCriteria() {}

    public LandCompensationCriteria(LandCompensationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hissaType = other.hissaType == null ? null : other.hissaType.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.sharePercentage = other.sharePercentage == null ? null : other.sharePercentage.copy();
        this.landMarketValue = other.landMarketValue == null ? null : other.landMarketValue.copy();
        this.structuralCompensation = other.structuralCompensation == null ? null : other.structuralCompensation.copy();
        this.horticultureCompensation = other.horticultureCompensation == null ? null : other.horticultureCompensation.copy();
        this.forestCompensation = other.forestCompensation == null ? null : other.forestCompensation.copy();
        this.solatiumMoney = other.solatiumMoney == null ? null : other.solatiumMoney.copy();
        this.additionalCompensation = other.additionalCompensation == null ? null : other.additionalCompensation.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.orderDate = other.orderDate == null ? null : other.orderDate.copy();
        this.paymentAmount = other.paymentAmount == null ? null : other.paymentAmount.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
        this.khatedarId = other.khatedarId == null ? null : other.khatedarId.copy();
        this.surveyId = other.surveyId == null ? null : other.surveyId.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.paymentAdviceId = other.paymentAdviceId == null ? null : other.paymentAdviceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LandCompensationCriteria copy() {
        return new LandCompensationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public HissaTypeFilter getHissaType() {
        return hissaType;
    }

    public HissaTypeFilter hissaType() {
        if (hissaType == null) {
            hissaType = new HissaTypeFilter();
        }
        return hissaType;
    }

    public void setHissaType(HissaTypeFilter hissaType) {
        this.hissaType = hissaType;
    }

    public DoubleFilter getArea() {
        return area;
    }

    public DoubleFilter area() {
        if (area == null) {
            area = new DoubleFilter();
        }
        return area;
    }

    public void setArea(DoubleFilter area) {
        this.area = area;
    }

    public DoubleFilter getSharePercentage() {
        return sharePercentage;
    }

    public DoubleFilter sharePercentage() {
        if (sharePercentage == null) {
            sharePercentage = new DoubleFilter();
        }
        return sharePercentage;
    }

    public void setSharePercentage(DoubleFilter sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    public DoubleFilter getLandMarketValue() {
        return landMarketValue;
    }

    public DoubleFilter landMarketValue() {
        if (landMarketValue == null) {
            landMarketValue = new DoubleFilter();
        }
        return landMarketValue;
    }

    public void setLandMarketValue(DoubleFilter landMarketValue) {
        this.landMarketValue = landMarketValue;
    }

    public DoubleFilter getStructuralCompensation() {
        return structuralCompensation;
    }

    public DoubleFilter structuralCompensation() {
        if (structuralCompensation == null) {
            structuralCompensation = new DoubleFilter();
        }
        return structuralCompensation;
    }

    public void setStructuralCompensation(DoubleFilter structuralCompensation) {
        this.structuralCompensation = structuralCompensation;
    }

    public DoubleFilter getHorticultureCompensation() {
        return horticultureCompensation;
    }

    public DoubleFilter horticultureCompensation() {
        if (horticultureCompensation == null) {
            horticultureCompensation = new DoubleFilter();
        }
        return horticultureCompensation;
    }

    public void setHorticultureCompensation(DoubleFilter horticultureCompensation) {
        this.horticultureCompensation = horticultureCompensation;
    }

    public DoubleFilter getForestCompensation() {
        return forestCompensation;
    }

    public DoubleFilter forestCompensation() {
        if (forestCompensation == null) {
            forestCompensation = new DoubleFilter();
        }
        return forestCompensation;
    }

    public void setForestCompensation(DoubleFilter forestCompensation) {
        this.forestCompensation = forestCompensation;
    }

    public DoubleFilter getSolatiumMoney() {
        return solatiumMoney;
    }

    public DoubleFilter solatiumMoney() {
        if (solatiumMoney == null) {
            solatiumMoney = new DoubleFilter();
        }
        return solatiumMoney;
    }

    public void setSolatiumMoney(DoubleFilter solatiumMoney) {
        this.solatiumMoney = solatiumMoney;
    }

    public DoubleFilter getAdditionalCompensation() {
        return additionalCompensation;
    }

    public DoubleFilter additionalCompensation() {
        if (additionalCompensation == null) {
            additionalCompensation = new DoubleFilter();
        }
        return additionalCompensation;
    }

    public void setAdditionalCompensation(DoubleFilter additionalCompensation) {
        this.additionalCompensation = additionalCompensation;
    }

    public CompensationStatusFilter getStatus() {
        return status;
    }

    public CompensationStatusFilter status() {
        if (status == null) {
            status = new CompensationStatusFilter();
        }
        return status;
    }

    public void setStatus(CompensationStatusFilter status) {
        this.status = status;
    }

    public LocalDateFilter getOrderDate() {
        return orderDate;
    }

    public LocalDateFilter orderDate() {
        if (orderDate == null) {
            orderDate = new LocalDateFilter();
        }
        return orderDate;
    }

    public void setOrderDate(LocalDateFilter orderDate) {
        this.orderDate = orderDate;
    }

    public DoubleFilter getPaymentAmount() {
        return paymentAmount;
    }

    public DoubleFilter paymentAmount() {
        if (paymentAmount == null) {
            paymentAmount = new DoubleFilter();
        }
        return paymentAmount;
    }

    public void setPaymentAmount(DoubleFilter paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public StringFilter getTransactionId() {
        return transactionId;
    }

    public StringFilter transactionId() {
        if (transactionId == null) {
            transactionId = new StringFilter();
        }
        return transactionId;
    }

    public void setTransactionId(StringFilter transactionId) {
        this.transactionId = transactionId;
    }

    public LongFilter getKhatedarId() {
        return khatedarId;
    }

    public LongFilter khatedarId() {
        if (khatedarId == null) {
            khatedarId = new LongFilter();
        }
        return khatedarId;
    }

    public void setKhatedarId(LongFilter khatedarId) {
        this.khatedarId = khatedarId;
    }

    public LongFilter getSurveyId() {
        return surveyId;
    }

    public LongFilter surveyId() {
        if (surveyId == null) {
            surveyId = new LongFilter();
        }
        return surveyId;
    }

    public void setSurveyId(LongFilter surveyId) {
        this.surveyId = surveyId;
    }

    public LongFilter getProjectLandId() {
        return projectLandId;
    }

    public LongFilter projectLandId() {
        if (projectLandId == null) {
            projectLandId = new LongFilter();
        }
        return projectLandId;
    }

    public void setProjectLandId(LongFilter projectLandId) {
        this.projectLandId = projectLandId;
    }

    public LongFilter getPaymentAdviceId() {
        return paymentAdviceId;
    }

    public LongFilter paymentAdviceId() {
        if (paymentAdviceId == null) {
            paymentAdviceId = new LongFilter();
        }
        return paymentAdviceId;
    }

    public void setPaymentAdviceId(LongFilter paymentAdviceId) {
        this.paymentAdviceId = paymentAdviceId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LandCompensationCriteria that = (LandCompensationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(hissaType, that.hissaType) &&
            Objects.equals(area, that.area) &&
            Objects.equals(sharePercentage, that.sharePercentage) &&
            Objects.equals(landMarketValue, that.landMarketValue) &&
            Objects.equals(structuralCompensation, that.structuralCompensation) &&
            Objects.equals(horticultureCompensation, that.horticultureCompensation) &&
            Objects.equals(forestCompensation, that.forestCompensation) &&
            Objects.equals(solatiumMoney, that.solatiumMoney) &&
            Objects.equals(additionalCompensation, that.additionalCompensation) &&
            Objects.equals(status, that.status) &&
            Objects.equals(orderDate, that.orderDate) &&
            Objects.equals(paymentAmount, that.paymentAmount) &&
            Objects.equals(transactionId, that.transactionId) &&
            Objects.equals(khatedarId, that.khatedarId) &&
            Objects.equals(surveyId, that.surveyId) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(paymentAdviceId, that.paymentAdviceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            hissaType,
            area,
            sharePercentage,
            landMarketValue,
            structuralCompensation,
            horticultureCompensation,
            forestCompensation,
            solatiumMoney,
            additionalCompensation,
            status,
            orderDate,
            paymentAmount,
            transactionId,
            khatedarId,
            surveyId,
            projectLandId,
            paymentAdviceId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LandCompensationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hissaType != null ? "hissaType=" + hissaType + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (sharePercentage != null ? "sharePercentage=" + sharePercentage + ", " : "") +
            (landMarketValue != null ? "landMarketValue=" + landMarketValue + ", " : "") +
            (structuralCompensation != null ? "structuralCompensation=" + structuralCompensation + ", " : "") +
            (horticultureCompensation != null ? "horticultureCompensation=" + horticultureCompensation + ", " : "") +
            (forestCompensation != null ? "forestCompensation=" + forestCompensation + ", " : "") +
            (solatiumMoney != null ? "solatiumMoney=" + solatiumMoney + ", " : "") +
            (additionalCompensation != null ? "additionalCompensation=" + additionalCompensation + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (orderDate != null ? "orderDate=" + orderDate + ", " : "") +
            (paymentAmount != null ? "paymentAmount=" + paymentAmount + ", " : "") +
            (transactionId != null ? "transactionId=" + transactionId + ", " : "") +
            (khatedarId != null ? "khatedarId=" + khatedarId + ", " : "") +
            (surveyId != null ? "surveyId=" + surveyId + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (paymentAdviceId != null ? "paymentAdviceId=" + paymentAdviceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
