package com.melontech.landsys.service.criteria;

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
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.melontech.landsys.domain.PaymentAdviceDetails} entity. This class is used
 * in {@link com.melontech.landsys.web.rest.PaymentAdviceDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payment-advice-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PaymentAdviceDetailsCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter landOwners;

    private HissaTypeFilter hissaType;

    private LongFilter paymentAdviceId;

    private LongFilter projectLandId;

    private LongFilter khatedarId;

    private Boolean distinct;

    public PaymentAdviceDetailsCriteria() {}

    public PaymentAdviceDetailsCriteria(PaymentAdviceDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.landOwners = other.landOwners == null ? null : other.landOwners.copy();
        this.hissaType = other.hissaType == null ? null : other.hissaType.copy();
        this.paymentAdviceId = other.paymentAdviceId == null ? null : other.paymentAdviceId.copy();
        this.projectLandId = other.projectLandId == null ? null : other.projectLandId.copy();
        this.khatedarId = other.khatedarId == null ? null : other.khatedarId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentAdviceDetailsCriteria copy() {
        return new PaymentAdviceDetailsCriteria(this);
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

    public StringFilter getLandOwners() {
        return landOwners;
    }

    public StringFilter landOwners() {
        if (landOwners == null) {
            landOwners = new StringFilter();
        }
        return landOwners;
    }

    public void setLandOwners(StringFilter landOwners) {
        this.landOwners = landOwners;
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
        final PaymentAdviceDetailsCriteria that = (PaymentAdviceDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(landOwners, that.landOwners) &&
            Objects.equals(hissaType, that.hissaType) &&
            Objects.equals(paymentAdviceId, that.paymentAdviceId) &&
            Objects.equals(projectLandId, that.projectLandId) &&
            Objects.equals(khatedarId, that.khatedarId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, landOwners, hissaType, paymentAdviceId, projectLandId, khatedarId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentAdviceDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (landOwners != null ? "landOwners=" + landOwners + ", " : "") +
            (hissaType != null ? "hissaType=" + hissaType + ", " : "") +
            (paymentAdviceId != null ? "paymentAdviceId=" + paymentAdviceId + ", " : "") +
            (projectLandId != null ? "projectLandId=" + projectLandId + ", " : "") +
            (khatedarId != null ? "khatedarId=" + khatedarId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
