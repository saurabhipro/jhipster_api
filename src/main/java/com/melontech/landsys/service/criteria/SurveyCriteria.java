package com.melontech.landsys.service.criteria;

import com.melontech.landsys.domain.enumeration.HissaType;
import com.melontech.landsys.domain.enumeration.SurveyStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.Survey} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.SurveyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /surveys?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class SurveyCriteria implements Serializable, Criteria {

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
     * Class for filtering SurveyStatus
     */
    public static class SurveyStatusFilter extends Filter<SurveyStatus> {

        public SurveyStatusFilter() {}

        public SurveyStatusFilter(SurveyStatusFilter filter) {
            super(filter);
        }

        @Override
        public SurveyStatusFilter copy() {
            return new SurveyStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter surveyor;

    private HissaTypeFilter hissaType;

    private DoubleFilter sharePercentage;

    private DoubleFilter area;

    private DoubleFilter landMarketValue;

    private DoubleFilter structuralValue;

    private DoubleFilter horticultureValue;

    private DoubleFilter forestValue;

    private DoubleFilter distanceFromCity;

    private StringFilter remarks;

    private SurveyStatusFilter status;

    private LongFilter projectLandId;

    private LongFilter landCompensationId;

    private LongFilter paymentAdviceId;

    private LongFilter paymentFileId;

    private Boolean distinct;

    public SurveyCriteria() {}

    public SurveyCriteria(SurveyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.surveyor = other.surveyor == null ? null : other.surveyor.copy();
        this.hissaType = other.hissaType == null ? null : other.hissaType.copy();
        this.sharePercentage = other.sharePercentage == null ? null : other.sharePercentage.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.landMarketValue = other.landMarketValue == null ? null : other.landMarketValue.copy();
        this.structuralValue = other.structuralValue == null ? null : other.structuralValue.copy();
        this.horticultureValue = other.horticultureValue == null ? null : other.horticultureValue.copy();
        this.forestValue = other.forestValue == null ? null : other.forestValue.copy();
        this.distanceFromCity = other.distanceFromCity == null ? null : other.distanceFromCity.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.landCompensationId = other.landCompensationId == null ? null : other.landCompensationId.copy();
        this.paymentAdviceId = other.paymentAdviceId == null ? null : other.paymentAdviceId.copy();
        this.paymentFileId = other.paymentFileId == null ? null : other.paymentFileId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SurveyCriteria copy() {
        return new SurveyCriteria(this);
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

    public StringFilter getSurveyor() {
        return surveyor;
    }

    public StringFilter surveyor() {
        if (surveyor == null) {
            surveyor = new StringFilter();
        }
        return surveyor;
    }

    public void setSurveyor(StringFilter surveyor) {
        this.surveyor = surveyor;
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

    public DoubleFilter getStructuralValue() {
        return structuralValue;
    }

    public DoubleFilter structuralValue() {
        if (structuralValue == null) {
            structuralValue = new DoubleFilter();
        }
        return structuralValue;
    }

    public void setStructuralValue(DoubleFilter structuralValue) {
        this.structuralValue = structuralValue;
    }

    public DoubleFilter getHorticultureValue() {
        return horticultureValue;
    }

    public DoubleFilter horticultureValue() {
        if (horticultureValue == null) {
            horticultureValue = new DoubleFilter();
        }
        return horticultureValue;
    }

    public void setHorticultureValue(DoubleFilter horticultureValue) {
        this.horticultureValue = horticultureValue;
    }

    public DoubleFilter getForestValue() {
        return forestValue;
    }

    public DoubleFilter forestValue() {
        if (forestValue == null) {
            forestValue = new DoubleFilter();
        }
        return forestValue;
    }

    public void setForestValue(DoubleFilter forestValue) {
        this.forestValue = forestValue;
    }

    public DoubleFilter getDistanceFromCity() {
        return distanceFromCity;
    }

    public DoubleFilter distanceFromCity() {
        if (distanceFromCity == null) {
            distanceFromCity = new DoubleFilter();
        }
        return distanceFromCity;
    }

    public void setDistanceFromCity(DoubleFilter distanceFromCity) {
        this.distanceFromCity = distanceFromCity;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public StringFilter remarks() {
        if (remarks == null) {
            remarks = new StringFilter();
        }
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public SurveyStatusFilter getStatus() {
        return status;
    }

    public SurveyStatusFilter status() {
        if (status == null) {
            status = new SurveyStatusFilter();
        }
        return status;
    }

    public void setStatus(SurveyStatusFilter status) {
        this.status = status;
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

    public LongFilter getLandCompensationId() {
        return landCompensationId;
    }

    public LongFilter landCompensationId() {
        if (landCompensationId == null) {
            landCompensationId = new LongFilter();
        }
        return landCompensationId;
    }

    public void setLandCompensationId(LongFilter landCompensationId) {
        this.landCompensationId = landCompensationId;
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

    public LongFilter getPaymentFileId() {
        return paymentFileId;
    }

    public LongFilter paymentFileId() {
        if (paymentFileId == null) {
            paymentFileId = new LongFilter();
        }
        return paymentFileId;
    }

    public void setPaymentFileId(LongFilter paymentFileId) {
        this.paymentFileId = paymentFileId;
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
        final SurveyCriteria that = (SurveyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(surveyor, that.surveyor) &&
            Objects.equals(hissaType, that.hissaType) &&
            Objects.equals(sharePercentage, that.sharePercentage) &&
            Objects.equals(area, that.area) &&
            Objects.equals(landMarketValue, that.landMarketValue) &&
            Objects.equals(structuralValue, that.structuralValue) &&
            Objects.equals(horticultureValue, that.horticultureValue) &&
            Objects.equals(forestValue, that.forestValue) &&
            Objects.equals(distanceFromCity, that.distanceFromCity) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(status, that.status) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(landCompensationId, that.landCompensationId) &&
            Objects.equals(paymentAdviceId, that.paymentAdviceId) &&
            Objects.equals(paymentFileId, that.paymentFileId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            surveyor,
            hissaType,
            sharePercentage,
            area,
            landMarketValue,
            structuralValue,
            horticultureValue,
            forestValue,
            distanceFromCity,
            remarks,
            status,
            projectLandId,
            landCompensationId,
            paymentAdviceId,
            paymentFileId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (surveyor != null ? "surveyor=" + surveyor + ", " : "") +
            (hissaType != null ? "hissaType=" + hissaType + ", " : "") +
            (sharePercentage != null ? "sharePercentage=" + sharePercentage + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (landMarketValue != null ? "landMarketValue=" + landMarketValue + ", " : "") +
            (structuralValue != null ? "structuralValue=" + structuralValue + ", " : "") +
            (horticultureValue != null ? "horticultureValue=" + horticultureValue + ", " : "") +
            (forestValue != null ? "forestValue=" + forestValue + ", " : "") +
            (distanceFromCity != null ? "distanceFromCity=" + distanceFromCity + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (landCompensationId != null ? "landCompensationId=" + landCompensationId + ", " : "") +
            (paymentAdviceId != null ? "paymentAdviceId=" + paymentAdviceId + ", " : "") +
            (paymentFileId != null ? "paymentFileId=" + paymentFileId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
